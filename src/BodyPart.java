import java.util.Random;

public enum BodyPart {
    LEFT_ARM,
    RIGHT_ARM,
    LEFT_LEG,
    RIGHT_LEG,
    BODY,
    HEAD;
    private static final Random RANDOM = new Random();
    public static BodyPart randomBodyPart() {
        BodyPart[] bodyParts = values();
        return bodyParts[RANDOM.nextInt(bodyParts.length)];
    }
}
