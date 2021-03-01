#!/bin/bash
for d in $(find . -maxdepth 1 -type d)
do
#Do something, the directory is accessible with $d:
    echo directory: $d
    for file in $d/* ; do
        mv "$file" "$(echo $file|sed -e 's/\([A-Z]\)/_\L\1/g' -e 's/^.\/_//')"
    done
done


