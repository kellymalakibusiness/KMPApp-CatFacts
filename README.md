#CatFacts
This is a simple app that is used to display random facts about cats. A user can interact with the facts by liking, disliking or saving them, to be able to view them later.

The main purpose of this app is to showcase the ability of Kotlin Multiplatform apps sharing the ui in both ios and android using jetpack compose.

##Tools
The app is built by Kotlin Multiplatform Mobile using the same user interface with compose.

It also uses two external public apis to get access to it's content
1. CatFacts API - A public api by white rabbit called meowfacts
https://github.com/wh-iterabb-it/meowfacts?ref=public_apis

2. Cat Images API - A public api providing a list of images of cats
https://developers.thecatapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=bOoHBz-8t

3. Realm - Database for persisting saved facts locally on the device

##Features
A user can customize their account by providing a profile picture and name which are just stored locally on the device.

A user can also interact by the facts displayed to them by either liking, disliking or saving the facts. They can also view all their progress from the profile screen where they can also be able to interact with facts that they saved.

##Others
Below is a link to the design file used for the app
https://www.figma.com/design/PuQWhoIofiKFGX1OkKTb7x/Cat-Facts-V1?node-id=77-1466&t=2IKcKVkzEMCFVL1i-1
