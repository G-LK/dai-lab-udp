package dai;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Musician {
    public String uuid;
    public String instrument;
    public Long lastActivity;

    static Map<String, String> instrumentsBySound = new HashMap<>();
    static {
        instrumentsBySound.put("ti-ta-ti", "piano");
        instrumentsBySound.put("pouet", "trumpet");
        instrumentsBySound.put("trulu", "flute");
        instrumentsBySound.put("gzi-gzi", "violin");
        instrumentsBySound.put("boum-boum", "drum");
    }

    public Musician() {
    }

    public Musician(String uuid, String instrument, Long lastActivity) {
        this.uuid = uuid;
        this.instrument = instrument;
        this.lastActivity = lastActivity;
    }

    public static final Map<String, Musician> musicians = new ConcurrentHashMap<>();

    static {
        musicians.put("salut", new Musician("salut", "flute", Long.valueOf(1232100000)));
        musicians.put("yo", new Musician("yo", "violo", Long.valueOf(22320)));
    }

    public void dropInactiveMusicians() {
        for (var entry : musicians.entrySet()) {
            if (entry.getValue().lastActivity < System.currentTimeMillis() - 5000) {
                musicians.remove(entry.getKey());
            }
        }
    }
}
