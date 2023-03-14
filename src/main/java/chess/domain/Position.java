package chess.domain;

import java.util.Objects;

public final class Position {

    private static final int MIN_POSITION = 0;
    private static final int MAX_POSITION = 7;

    private final int rank;
    private final int file;

    public Position(int rank, int file) {
        validateRange(rank);
        validateRange(file);
        this.rank = rank;
        this.file = file;
    }

    private void validateRange(int position) {
        if (position < MIN_POSITION || position > MAX_POSITION) {
            throw new IllegalArgumentException("[ERROR] 잘못된 위치입니다.");
        }
    }

    public Position moveBy(int diffRank, int diffFile) {
        return new Position(rank + diffRank, file + diffFile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return rank == position.rank && file == position.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }
}
