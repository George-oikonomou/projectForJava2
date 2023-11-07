# <ins>#Installation</ins>

Go to a desired folder,open the terminal inside the folder and run the following command:

```git clone https://github.com/George-oikonomou/projectForJava2.git```

# <ins>#documentation for devs</ins>

### <li><ins>Useful GitHub commands:</ins></li>

### ***```git checkout```***
switches between branches,
by default you are on the master branch

when you want to make changes to the project you have to create a new branch and work on it

to make a new branch you have to run the following command:

```git checkout -b [branch name]```

branch name should be precise and describe the changes you are going to make on the project

typical examples: category/name-of-the-branch

bug/fixing-bug-name

feature/adding-feature-name

refactor/refactoring-feature-name 

to check out to an existing branch 

```git checkout [branch name]```


### ***```git status```*** 
tells you the status of your current branch 

example:any changes on your local repository compared to the origin(the one on GitHub) 

[more info](https://www.google.com/search?q=git+status&oq=git+status&gs_lcrp=EgZjaHJvbWUyCQgAEEUYORiABDIHCAEQABiABDIHCAIQABiABDIHCAMQABiABDIHCAQQABiABDIHCAUQABiABDIGCAYQRRg8MgYIBxBFGEHSAQgzMzExajBqNKgCALACAA&sourceid=chrome&ie=UTF-8)

### ***```git add [arg]```***
adds the changes you have made to a specific file or all the files in the current directory to the staging area 

for specific files: ***```git add [arg]```***


for all files : ***```git add .```***
[more info](https://www.google.com/search?q=git+add&oq=git+add+&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIHCAEQABiABDIHCAIQABiABDIHCAMQABiABDIHCAQQABiABDIGCAUQRRg8MgYIBhBFGDwyBggHEEUYPNIBCDE2MTVqMGo5qAIAsAIA&sourceid=chrome&ie=UTF-8)


### ***```git restore [arg]```***

in case you want to discard the changes you have made to a specific file or all the files in the current directory

all files: ***```git restore .```***

specific file: ***```git restore [route to specific file]```*** (you can find that in ***```git status```***
)

### ***```git commit -m [message]```***
after you have finished making some changes to your local repository you have to commit them to the local repository with a message that describes the changes you have made


you can choose to group them in a single commit or make multiple commits split in categories for each logical change you have made
(optional-helps during code review) [more info](https://www.atlassian.com/git/tutorials/saving-changes/git-commit#:~:text=The%20git%20commit%20command%20captures,you%20explicitly%20ask%20it%20to.)

### ***```git push```***

after you have committed your changes to the local repository you have to push them to the origin(the one on GitHub) so that the other collaborators can see them and work on them
[more info](https://github.com/git-guides/git-push)
### ***```git pull```***

if you want to get the latest version of the project from the origin(the one on GitHub) you have to pull it to your local repository


this pulls the changes from a specific origin repository to your specific local repository accordingly to the branch you are currently on
[more info](https://github.com/git-guides/git-pull)

# <ins>#Testing</ins>
unit tests are used to ensure that when one of us changes something in the code, the rest of the code is not affected.

Unit tests must be written in as many methods as possible.

especially the ones that take long to be tested manually.

the more tests we write the less time we will spend on debugging.
# <ins>#collaborators</ins>

<li><em>George Oikonomou</em></li>

<li><em>Spyros Georgiou</em></li>

<li><em>Georgia Vrettakou</em></li>
