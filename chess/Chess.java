
package chess;

public class Chess {
    enum Player { white, black }

    private static gs st = new gs();
    public static ReturnPlay play(String move) {
        if (move == null) move = "";
        String s = move.trim();
        if (st.ov) {
            ReturnPlay rp = new ReturnPlay();
            rp.piecesOnBoard = st.rt();
            rp.message = st.om;
            return rp;
        }
        if (s.length() == 0) {
            ReturnPlay rp = new ReturnPlay();
            rp.piecesOnBoard = st.rt();
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        ReturnPlay rp = new ReturnPlay();
        rp.piecesOnBoard = st.rt();
        rp.message = null;
        if (s.equals("resign")) {
            rp.message = (st.tn == Player.white)
                    ? ReturnPlay.Message.RESIGN_BLACK_WINS
                    : ReturnPlay.Message.RESIGN_WHITE_WINS;
            st.ov = true;
            st.om = rp.message;
            return rp;
        }
        pr p = mp.ps(s);
        if (p.f.f == 0 && p.f.r == 0 && p.t.f == 0 && p.t.r == 0 && !s.equals("a1 a1")) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        pc a = st.b.g(p.f);
        if (a == null || a.o != st.tn) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        mv rq = new mv(p.f, p.t);
        if (p.pl != null) {
            char c = p.pl;
            if (c == 'N') rq.p = (st.tn == Player.white) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
            if (c == 'R') rq.p = (st.tn == Player.white) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
            if (c == 'B') rq.p = (st.tn == Player.white) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
            if (c == 'Q') rq.p = (st.tn == Player.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        }
        mv m = st.fm(a, rq);
        if (m == null) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        m.p = rq.p;
        if (a.t == ReturnPiece.PieceType.WP || a.t == ReturnPiece.PieceType.BP) {
            if (m.p == null) {
                if ((a.o == Player.white && m.t.r == 7) || (a.o == Player.black && m.t.r == 0)) {
                    m.p = (a.o == Player.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
                }
            }
        } else {
            m.p = null; // only pawns promote
        }
        gs cst = st.cp();
        if (!cst.ap(m)) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        if (cst.ic(st.tn)) {
            rp.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return rp;
        }
        st.ap(m);
        rp.piecesOnBoard = st.rt();
        if (p.d) {
            rp.message = ReturnPlay.Message.DRAW;
            st.ov = true;
            st.om = rp.message;
            return rp;
        }
        Player nx = st.tn; // next to move
        boolean chk = st.ic(nx);
        boolean hlm = st.hl(nx);
        if (chk && !hlm) {
            rp.message = (nx == Player.white)
                    ? ReturnPlay.Message.CHECKMATE_BLACK_WINS
                    : ReturnPlay.Message.CHECKMATE_WHITE_WINS;
            st.ov = true;
            st.om = rp.message;
        } else if (!chk && !hlm) {
            rp.message = ReturnPlay.Message.STALEMATE;
            st.ov = true;
            st.om = rp.message;
        } else if (chk) {
            rp.message = ReturnPlay.Message.CHECK;
        } else {
            rp.message = null;
        }
        return rp;
    }
    public static void start() {
        st = new gs();
        st.rs();
    }
}