git remote add changes https://github.com/csu314sp18/Changes
git fetch changes
git merge --allow-unrelated-histories changes/master
git remote remove changes

# The url for the Changes repository should reflect the current semester
