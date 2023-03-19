package chess.domain;

import static chess.domain.MoveStrategy.*;

import chess.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board() {
        board = new HashMap<>();
        init();
    }

    private void init() {
        for (PieceSettings pieceSetting : PieceSettings.values()) {
            board.put(pieceSetting.getPosition(), pieceSetting.getPiece());
        }
    }

    public void move(Position source, Position target) {
        validateSource(source);
        validateMovable(source, target);
        Piece sourcePiece = board.get(source);
        if (sourcePiece.isPawn()) {
            validatePawnMoving(source, target);
        }
        validateSameTeam(source, target);
        validatePathBeforeTarget(sourcePiece.findPath(source, target));

        movePiece(source, target);
    }

    private void validateSource(Position source) {
        if (!board.containsKey(source)) {
            throw new IllegalArgumentException("[ERROR] source 위치에 기물이 없습니다.");
        }
    }

    private void validateMovable(Position source, Position target) {
        Piece sourcePiece = board.get(source);

        if (!sourcePiece.isMovable(source, target)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없는 위치입니다.");
        }
    }

    private void validatePathBeforeTarget(List<Position> path) {
        for (int index = 0; index < path.size() - 1; index++) {
            validateBlocked(path, index);
        }
    }

    private void validateBlocked(List<Position> path, int index) {
        if (board.containsKey(path.get(index))) {
            throw new IllegalArgumentException("[ERROR] 이동 경로에 기물이 있습니다.");
        }
    }

    private void validateSameTeam(Position source, Position target) {
        if (!board.containsKey(target)) {
            return;
        }

        Piece sourcePiece = board.get(source);
        Piece targetPiece = board.get(target);

        if (targetPiece.isSameTeam(sourcePiece)) {
            throw new IllegalArgumentException("[ERROR] 타겟 위치에 같은 팀 기물이 있습니다.");
        }
    }

    private void validatePawnMoving(Position source, Position target) {
        if (!board.containsKey(target) && !PAWN_STRAIGHT.isMovable(source, target)) {
            throw new IllegalArgumentException("[ERROR] 폰은 상대 기물이 없을 경우, 대각선으로 움직일 수 없습니다.");
        }

        if (board.containsKey(target) && PAWN_STRAIGHT.isMovable(source, target)) {
            throw new IllegalArgumentException("[ERROR] 폰은 기물이 있는 곳으로 직진할 수 없습니다.");
        }
    }

    private void movePiece(Position source, Position target) {
        Piece piece = board.get(source);
        board.remove(source);
        board.put(target, piece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public boolean isTurn(Position source, Team turn) {
        validateSource(source);
        return board.get(source).isSameTeam(turn);
    }
}
