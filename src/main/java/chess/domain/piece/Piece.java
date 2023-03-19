package chess.domain.piece;

import chess.domain.game.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract boolean isMovable(Position source, Position target, Team team);

    protected abstract int calculateCount(int fileDiff, int rankDiff);

    public boolean isSameTeam(Team team) {
        return this.team.isSameTeam(team);
    }

    public boolean isNotSameTeam(Team team) {
        return !this.team.isSameTeam(team);
    }

    public Team team() {
        return team;
    }

    public List<Position> findPath(Position source, Position target) {
        int rankDiff = source.rankDiff(target);
        int fileDiff = source.fileDiff(target);

        int count = calculateCount(rankDiff, fileDiff);
        int rankUnit = rankDiff / count;
        int fileUnit = fileDiff / count;

        return makePath(count, rankUnit, fileUnit, source);
    }

    private List<Position> makePath(int count, int rankUnit, int fileUnit, Position current) {
        List<Position> path = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            current = current.moveBy(rankUnit, fileUnit);
            path.add(current);
        }
        return path;
    }
}
