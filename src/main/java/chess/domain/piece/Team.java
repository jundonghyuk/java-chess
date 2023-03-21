package chess.domain.piece;

public enum Team {

    BLACK,
    WHITE,
    EMPTY;

    public boolean isSameTeam(Team team) {
        return this == team;
    }

    public Team reverse() {
        if (WHITE == this) {
            return BLACK;
        }
        return WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public static Team getStartTeam() {
        return WHITE;
    }
}
