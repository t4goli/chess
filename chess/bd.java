package chess;

import java.util.ArrayList;

final class bd {
    private final pc[][] b = new pc[8][8];
    pc g(sq s) { return (s==null||!s.ok()) ? null : b[s.r][s.f]; }
    void s(sq q, pc p) { b[q.r][q.f] = p; }

    void mv(sq f, sq t) {
        pc p = g(f);
        s(t, p);
        s(f, null);
    }

    bd cp() {
        bd x = new bd();
        for (int r=0;r<8;r++) for (int f=0;f<8;f++) x.b[r][f] = this.b[r][f];
        return x;
    }
    ArrayList<sq> al() {
        ArrayList<sq> z = new ArrayList<>();
        for (int r=0;r<8;r++) for (int f=0;f<8;f++) if (b[r][f]!=null) z.add(new sq(f,r));
        return z;
    }
    sq fk(Chess.Player o) {
        ReturnPiece.PieceType k = (o==Chess.Player.white) ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
        for (int r=0;r<8;r++) for (int f=0;f<8;f++) {
            pc p = b[r][f];
            if (p!=null && p.t==k) return new sq(f,r);
        }
        return null;
    }
}