package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.particle.CustomParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.Random;

/**
 * <i>"Don't waste time spawning particles manually - let {@code ParticleBuilder} do the work for you!"</i>
 * <p></p>
 * Singleton class that builds particles. This is an alternative (and neater, I think) solution to vanilla's
 * varargs-based system. All building methods are chainable, so particles can be created using only one line of code,
 * similar to how {@code BufferBuilder} is used for drawing vertices. This class replaces the particle spawning methods
 * in wizardry's proxies.
 * <p></p>
 * {@link ParticleBuilder#instance} retrieves the static instance of the particle builder. Use
 * {@link ParticleBuilder#particle(ResourceLocation)} to start building a particle, or alternatively use the static
 * convenience version {@link ParticleBuilder#create(ResourceLocation)}. Use {@link ParticleBuilder#spawn(World)}
 * to finish building and spawn the particle. Between these two, a variety of parameters can be set using the various
 * setter methods (see individual method descriptions for more details). These, along with {@code ParticleBuilder.particle(...)},
 * return the particle builder instance, allowing them to be chained together to spawn particles using a single line of code.
 * If any parameters are unspecified these will default to certain values, which may or may not depend on the particle type.
 * Not all parameters affect all particles. Again, see individual method descriptions for more details.
 * <p></p>
 * For example, a typical call to the particle builder might look something like this:
 * <p></p>
 * <code>ParticleBuilder.create(Type.SPARKLE).pos(x, y, z).vel(vx, vy, vz).clr(r, g, b).spawn(world);</code>
 * <p></p>
 * It also goes without saying that <b>this class should only ever be used client-side</b>. Attempting to spawn particles
 * on the server side will not work and will print a warning to the console. {@code ParticleBuilder} is not threadsafe
 * and attempting to call it from multiple threads at once will very likely cause an {@link IllegalStateException}.
 *
 * @author Electroblob
 * @since Wizardry 4.2
 */
// The number of different combinations of parameters now required for the various particle
// types in wizardry made the method overloads in the proxies very cumbersome and inevitably resulted in redundant
// parameters, which made the code messy and hard to read. Those methods have now been removed.

// Strictly speaking, this isn't a builder class in the traditional sense, because rather than returning the built
// object at the end, it sends it to be processed instead and returns nothing. Additionally, unlike most builders
// it's a singleton, because it's likely to be called very frequently and since this only happens from a single (client)
// thread, there's no point making a new instance every time and clogging the heap with objects. It's also lazy, see
// the comment about builder variables below.
public final class ParticleBuilder {

	/** The static instance of the particle builder. */
	public static final ParticleBuilder instance = new ParticleBuilder();
	
	/** Whether the particle builder is currently building or not. */
	private boolean building = false;
	
	// Builder variables
	// We can't just store a particle and set its parameters in the builder methods, because the server won't like having
	// a field of a client-only type
	private ResourceLocation type;
	private double x, y, z;
	private double vx, vy, vz;
	private float r, g, b;
	private float fr, fg, fb;
	private double radius;
	private double rpt;
	private int lifetime;
	private boolean gravity;
	private boolean shaded;
	private boolean collide;
	private float scale;
	private Entity entity;
	private float yaw, pitch;
	private double tx, ty, tz;
	private double tvx, tvy, tvz;
	private Entity target;
	private long seed;
	private double length;
	
	/**
	 * {@link ResourceLocation} constants representing the different types of particle added by wizardry. These
	 * effectively replace the enum {@code WizardryParticleType} from previous versions.
	 * Individual constants have comments detailing their corresponding default parameters. A range of values indicates
	 * randomness.
	 */
	// This was originally an enum, but I think having 'Type' explicitly declared is quite nice so I've left it as a
	// nested class.
	public static class Type {
		/** 3D-rendered spark particle.<p></p><b>Defaults:</b><br>Lifetime: 1 tick<br> Colour: red */
		public static final ResourceLocation SPARK = new ResourceLocation(MineFantasyReforged.MOD_ID,"spark");
	}
	
	private ParticleBuilder(){
		reset();
	}
	
	// ============================================= Core builder methods =============================================
	
	/**
	 * Starts building a particle of the given type. Static convenience version of
	 * {@link ParticleBuilder#particle(ResourceLocation)}; makes code more concise.
	 * @param type The type of particle to build
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is already building.
	 */
	public static ParticleBuilder create(ResourceLocation type){
		return ParticleBuilder.instance.particle(type);
	}
	
	/**
	 * Starts building a particle of the given type.
	 * @param type The type of particle to build
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is already building.
	 */
	public ParticleBuilder particle(ResourceLocation type){
		if(building) throw new IllegalStateException("Already building! Particle being built: " + getCurrentParticleString());
		this.type = type;
		this.building = true;
		return this;
	}
	
	/** Gets a readable string representation of the current builder parameters; used in error messages. */
	private String getCurrentParticleString(){
		return String.format("[ Type: %s, Position: (%s, %s, %s), Velocity: (%s, %s, %s), Colour: (%s, %s, %s), "
				+ "Fade Colour: (%s, %s, %s), Radius: %s, Revs/tick: %s, Lifetime: %s, Gravity: %s, Shaded: %s, "
				+ "Scale: %s, Entity: %s ]",
				type, x, y, z, vx, vy, vz, r, g, b, fr, fg, fb, radius, rpt, lifetime, gravity, shaded, scale, entity);
	}
	
	/**
	 * Sets the position of the particle being built. If unspecified, this defaults to the origin (0, 0, 0). If an entity
	 * is specified using {@link ParticleBuilder#entity(Entity)}, this will be <i>relative to</i> that entity's position.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param x The x coordinate to set
	 * @param y The y coordinate to set
	 * @param z The z coordinate to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder pos(double x, double y, double z){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * Sets the position of the particle being built. This is a vector-based alternative to {@link ParticleBuilder#pos(
	 * double, double, double)}, allowing for even more concise code when a vector is available.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param pos A vector representing the coordinates of the particle to be built.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder pos(Vec3d pos){
		return pos(pos.x, pos.y, pos.z);
	}
	
	/**
	 * Sets the velocity of the particle being built. If unspecified, this defaults to the particle's default velocity,
	 * specified within its constructor.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param vx The x velocity to set
	 * @param vy The y velocity to set
	 * @param vz The z velocity to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder vel(double vx, double vy, double vz){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		return this;
	}
	
	/**
	 * Sets the velocity of the particle being built. This is a vector-based alternative to {@link ParticleBuilder#vel(
	 * double, double, double)}, allowing for even more concise code when a vector is available.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param vel A vector representing the velocity of the particle to be built.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder vel(Vec3d vel){
		return vel(vel.x, vel.y, vel.z);
	}
	
	/**
	 * Sets the colour of the particle being built. If unspecified, this defaults to the particle's default colour,
	 * specified within its constructor. <i>If all colour components are 0 or 1, at least one must have the float suffix
	 * ({@code f} or {@code F}) or the integer overload will be used instead, causing the particle to appear black!</i>
	 * @param r The red colour component to set; will be clamped to between 0 and 1
	 * @param g The green colour component to set; will be clamped to between 0 and 1
	 * @param b The blue colour component to set; will be clamped to between 0 and 1
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder clr(float r, float g, float b){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.r = MathHelper.clamp(r, 0, 1);
		this.g = MathHelper.clamp(g, 0, 1);
		this.b = MathHelper.clamp(b, 0, 1);
		return this;
	}

	/**
	 * Sets the colour of the particle being built. This is an 8-bit (0-255) integer version of
	 * {@link ParticleBuilder#clr(float, float, float)}.
	 * @param r The red colour component to set; will be clamped to between 0 and 255
	 * @param g The green colour component to set; will be clamped to between 0 and 255
	 * @param b The blue colour component to set; will be clamped to between 0 and 255
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder clr(int r, int g, int b){
		return this.clr(r/255f, g/255f, b/255f); // Yes, 255 is correct and not 256, or else we can't have pure white
	}

	/**
	 * Sets the colour of the particle being built. This is a 6-digit hex colour version of
	 * {@link ParticleBuilder#clr(float, float, float)}.
	 * @param hex The colour to be set, as a packed 6-digit hex integer (e.g. 0xff0000).
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder clr(int hex){
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = (hex & 0xFF);
		return this.clr(r, g, b);
	}
	
	/**
	 * Sets the fade colour of the particle being built. If unspecified, this defaults to the whatever the particle's base
	 * colour is. <i>If all colour components are 0 or 1, at least one must have the float suffix
	 * ({@code f} or {@code F}) or the integer overload will be used instead, causing the particle to appear black!</i>
	 * @param r The red colour component to set; will be clamped to between 0 and 1
	 * @param g The green colour component to set; will be clamped to between 0 and 1
	 * @param b The blue colour component to set; will be clamped to between 0 and 1
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder fade(float r, float g, float b){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.fr = MathHelper.clamp(r, 0, 1);
		this.fg = MathHelper.clamp(g, 0, 1);
		this.fb = MathHelper.clamp(b, 0, 1);
		return this;
	}

	/**
	 * Sets the fade colour of the particle being built. This is an 8-bit (0-255) integer version of
	 * {@link ParticleBuilder#fade(float, float, float)}.
	 * @param r The red colour component to set; will be clamped to between 0 and 255
	 * @param g The green colour component to set; will be clamped to between 0 and 255
	 * @param b The blue colour component to set; will be clamped to between 0 and 255
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder fade(int r, int g, int b){
		return this.fade(r/255f, g/255f, b/255f); // Yes, 255 is correct and not 256, or else we can't have pure white
	}

	/**
	 * Sets the fade colour of the particle being built. This is a 6-digit hex colour version of
	 * {@link ParticleBuilder#fade(float, float, float)}.
	 * @param hex The colour to be set, as a packed 6-digit hex integer (e.g. 0xff0000).
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder fade(int hex){
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = (hex & 0xFF);
		return this.fade(r, g, b);
	}
	
	/**
	 * Sets the scale of the particle being built. If unspecified, this defaults to 1.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param scale The scale to set, as a multiple of the particle's default scale
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder scale(float scale){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.scale = scale;
		return this;
	}
	
	/**
	 * Sets the lifetime of the particle being built. If unspecified, this defaults to the particle's default lifetime,
	 * specified within its constructor.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param lifetime The lifetime to set in ticks
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder time(int lifetime){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.lifetime = lifetime;
		return this;
	}

	/**
	 * Sets the seed of the particle being built. If unspecified, this defaults to the particle's default seed,
	 * specified within its constructor (this is normally chosen at random).
	 * <p></p>
	 * <i>Pro tip: to get a particle to stay the same while a continuous spell is in use (but change between casts),
	 * use {@code .seed(world.getTotalWorldTime() - ticksInUse)}.</i>
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param seed The seed to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder seed(long seed){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.seed = seed;
		return this;
	}
	
	/**
	 * Sets the spin parameters of the particle being built. If unspecified, these both default to 0.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param radius The rotation radius to set
	 * @param speed The rotation speed to set, in revolutions per tick
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder spin(double radius, double speed){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.radius = radius;
		this.rpt = speed;
		return this;
	}

	// Used to say Affects: {@link Type#ICE ICE}, {@link Type#SPARKLE SPARKLE} - not sure that's true any more
	/**
	 * Sets the gravity of the particle being built. If unspecified, this defaults to false.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param gravity True to enable gravity for the particle, false to disable
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder gravity(boolean gravity){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.gravity = gravity;
		return this;
	}
	
	/**
	 * Sets the shading of the particle being built. If unspecified, this defaults to false.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param shaded True to enable shading for the particle, false for full brightness
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder shaded(boolean shaded){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.shaded = shaded;
		return this;
	}
	
	/**
	 * Sets the collisions of the particle being built. If unspecified, this defaults to false.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param collide True to enable block collisions for the particle, false to disable
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder collide(boolean collide){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.collide = collide;
		return this;
	}
	
	/**
	 * Sets the entity of the particle being built. This will cause the particle to move with the given entity, and will
	 * make the position specified using {@link ParticleBuilder#pos(double, double, double)} <i>relative to</i> that
	 * entity's position.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param entity The entity to set (passing in null will do nothing but will not cause any problems, so for the sake
	 * of conciseness it is not necessary to perform a null check on the passed-in argument)
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder entity(Entity entity){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.entity = entity;
		return this;
	}
	
	/**
	 * Sets the rotation of the particle being built. If unspecified, the particle will use the default behaviour and
	 * rotate to face the viewer.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param yaw The yaw angle to set in degrees, where 0 is south.
	 * @param pitch The pitch angle to set in degrees, where 0 is horizontal.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder face(float yaw, float pitch){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.yaw = yaw;
		this.pitch = pitch;
		return this;
	}
	
	/**
	 * Sets the rotation of the particle being built. This is an {@code EnumFacing}-based alternative to {@link
	 * ParticleBuilder#face(float, float)} which sets the yaw and pitch to the appropriate angles for the given facing.
	 * For example, if the given facing is {@code NORTH}, the particle will render parallel to the north face of blocks.
	 * If unspecified, the particle will use the default behaviour and rotate to face the viewer.
	 * <p></p>
	 * <b>Affects:</b> All particle types
	 * @param direction The {@code EnumFacing} direction to set.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder face(EnumFacing direction){
		return face(direction.getHorizontalAngle(), direction.getAxis().isVertical() ? direction.getAxisDirection().getOffset() * 90 : 0);
	}

	// ============================================= Targeted-only methods =============================================
	
	/**
	 * Sets the target of the particle being built. This will cause the particle to stretch to touch the given position.
	 * @param x The target x-coordinate to set
	 * @param y The target y-coordinate to set
	 * @param z The target z-coordinate to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder target(double x, double y, double z){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.tx = x;
		this.ty = y;
		this.tz = z;
		return this;
	}
	
	/**
	 * Sets the target of the particle being built. This is a vector-based alternative to
	 * {@link ParticleBuilder#target(double, double, double)}, allowing for even more concise code when a vector is
	 * available.
	 * @param pos A vector representing the target position of the particle to be built.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder target(Vec3d pos){
		return target(pos.x, pos.y, pos.z);
	}

	/**
	 * Sets the target point velocity of the particle being built. This will cause the position it stretches to touch to move
	 * at the given velocity. Has no effect unless {@link ParticleBuilder#target(double, double, double)} or one of its
	 * overloads is also set.
	 * @param vx The target point x velocity to set
	 * @param vy The target point y velocity to set
	 * @param vz The target point z velocity to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder tvel(double vx, double vy, double vz){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.tvx = vx;
		this.tvy = vy;
		this.tvz = vz;
		return this;
	}

	/**
	 * Sets the target point velocity of the particle being built. This is a vector-based alternative to
	 * {@link ParticleBuilder#tvel(double, double, double)}, allowing for even more concise code when a vector is
	 * available.
	 * @param vel A vector representing the target point velocity of the particle to be built.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder tvel(Vec3d vel){
		return tvel(vel.x, vel.y, vel.z);
	}

	/**
	 * Sets the target and target velocity of the particle being built. This method takes an origin entity and a
	 * position and estimates the position of the target point based on the given entity's rotational velocities and its
	 * distance from the given position.
	 * @param length The length of the particle being built.
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder length(double length){
		this.length = length;
		return this;
	}
	
	/**
	 * Sets the target of the particle being built. This will cause the particle to stretch to touch the given entity.
	 * @param target The entity to set
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public ParticleBuilder target(Entity target){
		if(!building) throw new IllegalStateException("Not building yet!");
		this.target = target;
		return this;
	}

	/**
	 * Spawns the particle that has been built and resets the particle builder.
	 * @param world The world in which to spawn the particle
	 * @throws IllegalStateException if the particle builder is not yet building.
	 */
	public void spawn(World world){
		
		if(!building) throw new IllegalStateException("Not building yet!");

		if(x == 0 && y == 0 && z == 0 && entity == null) MineFantasyReforged.LOG.warn("Spawning particle at (0, 0, 0) - are you"
				+ " sure the position/entity has been set correctly?");
		
		if(!world.isRemote){
			MineFantasyReforged.LOG.warn("ParticleBuilder.spawn(...) called on the server side! ParticleBuilder has prevented a "
					+ "server crash, but calling it on the server will do nothing. Consider adding a world.isRemote check.");
			// Must stop here because the line after this if statement would crash the server!
			reset();
			return;
		}
		
		CustomParticle particle = MineFantasyReforged.PROXY.createParticle(type, world, x, y, z);
		
		if(particle == null){
			// No need to display a warning here, we already did it in the client proxy
			reset();
			return;
		}
		
		// Anything with an if statement here allows default values to be set in particle constructors
		if(!Double.isNaN(vx) && !Double.isNaN(vy) && !Double.isNaN(vz)) 	particle.setVelocity(vx, vy, vz);
		if(r >= 0 && g >= 0 && b >= 0) 										particle.setRBGColorF(r, g, b);
		if(fr >= 0 && fg >= 0 && fb >= 0)									particle.setFadeColour(fr, fg, fb);
		if(lifetime >= 0) 													particle.setMaxAge(lifetime);
		if(radius > 0) 														particle.setSpin(radius, rpt);
		if(!Float.isNaN(yaw) && !Float.isNaN(pitch)) 						particle.setFacing(yaw, pitch);
		if(seed != 0)														particle.setSeed(seed);
		if(!Double.isNaN(tvx) && !Double.isNaN(tvy) && !Double.isNaN(tvz)) 	particle.setTargetVelocity(tvx, tvy, tvz);
		if(length > 0)														particle.setLength(length);

		particle.multipleParticleScaleBy(scale);
		particle.setGravity(gravity);
		particle.setShaded(shaded);
		particle.setCollisions(collide);
		particle.setEntity(entity);
		particle.setTargetPosition(tx, ty, tz);
		particle.setTargetEntity(target);

		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		reset();
	}
	
	/** Resets the state of the particle builder and resets all the builder variables to their default values. */
	private void reset(){
		building = false;
		type = null;
		x = 0;
		y = 0;
		z = 0;
		// NaN indicates the velocity was not set (can't use -1 since it could very reasonably be -1)
		// For all other values -1 indicates the value was not set
		vx = Double.NaN;
		vy = Double.NaN;
		vz = Double.NaN;
		r = -1;
		g = -1;
		b = -1;
		fr = -1;
		fg = -1;
		fb = -1;
		radius = 0;
		rpt = 0;
		lifetime = -1;
		gravity = false;
		shaded = false;
		collide = false;
		scale = 1;
		entity = null;
		yaw = Float.NaN;
		pitch = Float.NaN;
		tx = Double.NaN;
		ty = Double.NaN;
		tz = Double.NaN;
		tvx = Double.NaN;
		tvy = Double.NaN;
		tvz = Double.NaN;
		target = null;
		seed = 0;
		length = -1;
	}
	
	// ============================================== Convenience methods ==============================================
	
	// These may seem to go against the whole point of this class, but of course they return the ParticleBuilder instance
	// so anything else can still be chained onto them - centralising commonly-used particle spawning patterns without
	// losing any of the flexibility of the particle builder. In addition, callers of these methods are still free to
	// change any of the parameters that were set within them afterwards.
	
	/**
	 * Starts building a particle of the given type and positions it randomly within the given entity's bounding box.
	 * Equivalent to calling {@code ParticleBuilder.create(type).pos(...)}; users should chain any additional builder
	 * methods onto this one and finish with {@code .spawn(world)} as normal.
	 * Used extensively with summoned creatures; makes code much neater and more concise.
	 * <p></p>
	 * <i>N.B. this does <b>not</b> cause the particle to move with the given entity.</i>
	 * @param type The type of particle to build
	 * @param entity The entity to position the particle at
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is already building.
	 */
	public static ParticleBuilder create(ResourceLocation type, Entity entity){
		
		double x = entity.posX + (entity.world.rand.nextDouble() - 0.5D) * (double)entity.width;
		double y = entity.posY + entity.world.rand.nextDouble() * (double)entity.height;
		double z = entity.posZ + (entity.world.rand.nextDouble() - 0.5D) * (double)entity.width;
		
		return ParticleBuilder.instance.particle(type).pos(x, y, z);
	}
	
	/**
	 * Starts building a particle of the given type and positions it randomly within the given radius of the given position,
	 * with velocity proportional to distance from the given position if move is true. Good for making explosion-type effects.
	 * Equivalent to calling {@code ParticleBuilder.create(type).pos(...).vel(...)}; users should chain any additional builder
	 * methods onto this one and finish with {@code .spawn(world)} as normal.
	 * @param type The type of particle to build
	 * @param random An RNG instance
	 * @param x The x coordinate of the centre of the region in which to position the particle
	 * @param y The y coordinate of the centre of the region in which to position the particle
	 * @param z The z coordinate of the centre of the region in which to position the particle
	 * @param radius The radius of the region in which to position the particle
	 * @param move Whether the particle should move outwards from the centre (note that if this is false, the particle's
	 * default velocity will apply)
	 * @return The particle builder instance, allowing other methods to be chained onto this one
	 * @throws IllegalStateException if the particle builder is already building.
	 */
	public static ParticleBuilder create(ResourceLocation type, Random random, double x, double y, double z, double radius, boolean move){
		
		double px = x + (random.nextDouble()*2 - 1) * radius;
		double py = y + (random.nextDouble()*2 - 1) * radius;
		double pz = z + (random.nextDouble()*2 - 1) * radius;
		
		if(move) return ParticleBuilder.instance.particle(type).pos(px, py, pz).vel(px-x, py-y, pz-z);
		
		return ParticleBuilder.instance.particle(type).pos(px, py, pz);
	}
}
