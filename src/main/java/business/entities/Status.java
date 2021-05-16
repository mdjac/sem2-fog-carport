package business.entities;

public enum Status {
    Request("Forespørgsel"),
    OfferSent("Tilbud afsendt"),
    OfferAccepted("Tilbud accepteret"),
    PickedUp("Afhentet");

    String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    public static Status fromString(String input) {
        for (Status status : Status.values()) {
            if (status.name.equalsIgnoreCase(input)) {
                return status;
            }
        }
        return null;
    }
}
