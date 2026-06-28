package chess;

final class mv {
    final sq f, t;
    boolean ck;
    boolean cq; // c q side
    boolean ep;
    ReturnPiece.PieceType p;
    mv(sq f, sq t) { this.f = f; this.t = t; }
    boolean sm(mv o) { return f.equals(o.f) && t.equals(o.t); }
}