# Kraken
Light-weight object-oriented Tab-API for the Bukkit/Spigot API

### Installation

#### Option 1: Maven repository 
    *Coming soon*
#### Option 2: JAR
  1. Download the [latest release](https://github.com/Alexandeh/Kraken/releases).
  2. Add the JAR to your project.
    + For Eclipse users, see [here](http://stackoverflow.com/questions/11033603/how-to-create-a-jar-with-external-libraries-included-in-eclipse).
    + For IntelliJ users, see [here](http://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project).

Instantiate Kraken in your onEnable:

  ```java
public void onEnable() {
  //All your other stuff
  new Kraken(this);
}
  
  ```
  
### Example Usage
``` java
@EventHandler
public void onPlayerTabCreateEvent(PlayerTabCreateEvent event) {
    PlayerTab playerTab = event.getPlayerTab();

    playerTab.getByPosition(0, 0).text("Static Text").send();
}
```

#### Result
![result](https://i.gyazo.com/3ca29baf4bce8d9402885a954b7dbcd6.png)
