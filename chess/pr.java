package chess;

final class pr {
    final sq f, t;
    final boolean d;
    final Character pl;
    final ReturnPiece.PieceType p;
    pr(sq f, sq t, boolean d, Character pl) {
        this.f = f; this.t = t; this.d = d; this.pl = pl;
        this.p = null;
    }
}