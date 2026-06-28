package chess;

import java.util.ArrayList;

final class gs {
    bd b = new bd();
    Chess.Player tn = Chess.Player.white;
    boolean wkm, war, whr, bkm, bar, bhr;
    sq ep = null;
    boolean ov = false;
    ReturnPlay.Message om = null;
    void rs() {
        b = new bd();
        tn = Chess.Player.white;
        wkm = war = whr = bkm = bar = bhr = false;
        ep = null;
        ov = false;
        om = null;
        br(Chess.Player.white, 0);
        br(Chess.Player.black, 7);
        for (int f = 0; f < 8; f++) {
            b.s(new sq(f, 1), new pc(Chess.Player.white, ReturnPiece.PieceType.WP));
            b.s(new sq(f, 6), new pc(Chess.Player.black, ReturnPiece.PieceType.BP));
        }
    }

    private void br(Chess.Player o, int r) {
        b.s(new sq(0, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR));
        b.s(new sq(1, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN));
        b.s(new sq(2, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB));
        b.s(new sq(3, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ));
        b.s(new sq(4, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK));
        b.s(new sq(5, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB));
        b.s(new sq(6, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN));
        b.s(new sq(7, r), new pc(o, o == Chess.Player.white ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR));
    }
    gs cp() {
        gs x = new gs();
        x.b = this.b.cp();
        x.tn = this.tn;
        x.wkm = this.wkm; x.war = this.war; x.whr = this.whr;
        x.bkm = this.bkm; x.bar = this.bar; x.bhr = this.bhr;
        x.ep = (this.ep == null) ? null : new sq(this.ep.f, this.ep.r);
        x.ov = this.ov;
        x.om = this.om;
        return x;
    }

    ArrayList<ReturnPiece> rt() {
        ArrayList<ReturnPiece> z = new ArrayList<>();
        for (sq q : b.al()) {
            pc p = b.g(q);
            ReturnPiece rp = new ReturnPiece();
            rp.pieceType = p.t;
            rp.pieceFile = ReturnPiece.PieceFile.values()[q.f];
            rp.pieceRank = q.r + 1;
            z.add(rp);
        }
        return z;
    }

    mv fm(pc a, mv rq) {
        ArrayList<mv> pm = pm(a, rq.f);
        for (mv m : pm) {
            if (!m.sm(rq)) continue;
            if (a.t != ReturnPiece.PieceType.WP && a.t != ReturnPiece.PieceType.BP) {
                m.p = null;
            }
            return m;
        }
        return null;
    }

    ArrayList<mv> pm(pc a, sq f) {
        ArrayList<mv> z = new ArrayList<>();
        ReturnPiece.PieceType t = a.t;
        if (t == ReturnPiece.PieceType.WP || t == ReturnPiece.PieceType.BP) {
            pn(a, f, z);
        } else if (t == ReturnPiece.PieceType.WN || t == ReturnPiece.PieceType.BN) {
            kn(a, f, z);
        } else if (t == ReturnPiece.PieceType.WB || t == ReturnPiece.PieceType.BB) {
            sl(a, f, z, new int[][]{{1,1},{1,-1},{-1,1},{-1,-1}});
        } else if (t == ReturnPiece.PieceType.WR || t == ReturnPiece.PieceType.BR) {
            sl(a, f, z, new int[][]{{1,0},{-1,0},{0,1},{0,-1}});
        } else if (t == ReturnPiece.PieceType.WQ || t == ReturnPiece.PieceType.BQ) {
            sl(a, f, z, new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}});
        } else {
            kg(a, f, z);
        }
        return z;
    }

    private void pn(pc a, sq f, ArrayList<mv> z) {
        int dir = (a.o == Chess.Player.white) ? 1 : -1;
        int sr  = (a.o == Chess.Player.white) ? 1 : 6;
        sq t1 = new sq(f.f, f.r + dir);
        if (t1.ok() && b.g(t1) == null) {
            z.add(new mv(f, t1));
            sq t2 = new sq(f.f, f.r + 2 * dir);
            if (f.r == sr && t2.ok() && b.g(t2) == null) z.add(new mv(f, t2));
        }

        for (int df : new int[]{-1, 1}) {
            sq c = new sq(f.f + df, f.r + dir);
            if (!c.ok()) continue;
            pc oc = b.g(c);
            if (oc != null && oc.o != a.o) z.add(new mv(f, c));
            if (ep != null && c.equals(ep)) {
                mv m = new mv(f, c);
                m.ep = true;
                z.add(m);
            }
        }
    }

    private void kn(pc a, sq f, ArrayList<mv> z) {
        int[][] d={{1,2},{2,1},{-1,2},{-2,1},{1,-2},{2,-1},{-1,-2},{-2,-1}};
        for (int[] x : d) {
            sq t = new sq(f.f + x[0], f.r + x[1]);
            if (!t.ok()) continue;
            pc oc = b.g(t);
            if (oc == null || oc.o != a.o) z.add(new mv(f, t));
        }
    }

    private void sl(pc a, sq f, ArrayList<mv> z, int[][] d) {
        for (int[] x : d) {
            int ff = f.f + x[0], rr = f.r + x[1];
            while (ff >= 0 && ff < 8 && rr >= 0 && rr < 8) {
                sq t = new sq(ff, rr);
                pc oc = b.g(t);
                if (oc == null) z.add(new mv(f, t));
                else {
                    if (oc.o != a.o) z.add(new mv(f, t));
                    break;
                }
                ff += x[0]; rr += x[1];
            }
        }
    }

    private void kg(pc a, sq f, ArrayList<mv> z) {
        for (int df=-1; df<=1; df++) for (int dr=-1; dr<=1; dr++) {
            if (df==0 && dr==0) continue;
            sq t = new sq(f.f + df, f.r + dr);
            if (!t.ok()) continue;
            pc oc = b.g(t);
            if (oc == null || oc.o != a.o) z.add(new mv(f, t));
        }

        if (a.o == Chess.Player.white && !wkm && f.f == 4 && f.r == 0) {
            if (!whr && b.g(new sq(5,0)) == null && b.g(new sq(6,0)) == null) {
                mv m = new mv(f, new sq(6,0)); m.ck = true; z.add(m);
            }
            if (!war && b.g(new sq(3,0)) == null && b.g(new sq(2,0)) == null && b.g(new sq(1,0)) == null) {
                mv m = new mv(f, new sq(2,0)); m.cq = true; z.add(m);
            }
        }

        if (a.o == Chess.Player.black && !bkm && f.f == 4 && f.r == 7) {
            if (!bhr && b.g(new sq(5,7)) == null && b.g(new sq(6,7)) == null) {
                mv m = new mv(f, new sq(6,7)); m.ck = true; z.add(m);
            }
            if (!bar && b.g(new sq(3,7)) == null && b.g(new sq(2,7)) == null && b.g(new sq(1,7)) == null) {
                mv m = new mv(f, new sq(2,7)); m.cq = true; z.add(m);
            }
        }
    }

    boolean hl(Chess.Player o) {
        for (sq q : b.al()) {
            pc p = b.g(q);
            if (p.o != o) continue;
            for (mv m : pm(p, q)) {
                gs c = cp();
                if (!c.ap(m)) continue;
                if (!c.ic(o)) return true;
            }
        }
        return false;
    }

    boolean ic(Chess.Player o) {
        sq k = b.fk(o);
        if (k == null) return false;
        Chess.Player at = (o == Chess.Player.white) ? Chess.Player.black : Chess.Player.white;
        return ia(at, k);
    }

    boolean ia(Chess.Player a, sq t) {
        for (sq q : b.al()) {
            pc p = b.g(q);
            if (p.o != a) continue;
            if (at(p, q, t)) return true;
        }
        return false;
    }

    private boolean at(pc p, sq f, sq t) {
        int df = t.f - f.f;
        int dr = t.r - f.r;
        if (p.t == ReturnPiece.PieceType.WP || p.t == ReturnPiece.PieceType.BP) {
            int dir = (p.o == Chess.Player.white) ? 1 : -1;
            return dr == dir && (df == 1 || df == -1);
        }
        if (p.t == ReturnPiece.PieceType.WN || p.t == ReturnPiece.PieceType.BN) {
            int adf = Math.abs(df), adr = Math.abs(dr);
            return (adf == 1 && adr == 2) || (adf == 2 && adr == 1);
        }
        if (p.t == ReturnPiece.PieceType.WK || p.t == ReturnPiece.PieceType.BK) {
            return Math.abs(df) <= 1 && Math.abs(dr) <= 1;
        }
        boolean rl = (p.t == ReturnPiece.PieceType.WR || p.t == ReturnPiece.PieceType.BR ||
                      p.t == ReturnPiece.PieceType.WQ || p.t == ReturnPiece.PieceType.BQ);
        boolean bl = (p.t == ReturnPiece.PieceType.WB || p.t == ReturnPiece.PieceType.BB ||
                      p.t == ReturnPiece.PieceType.WQ || p.t == ReturnPiece.PieceType.BQ);

        if (rl && (df == 0 || dr == 0)) return cr(f, t);
        if (bl && Math.abs(df) == Math.abs(dr) && df != 0) return cr(f, t);
        return false;
    }

    private boolean cr(sq f, sq t) {
        int sf = Integer.compare(t.f, f.f);
        int sr = Integer.compare(t.r, f.r);
        int ff = f.f + sf, rr = f.r + sr;
        while (ff != t.f || rr != t.r) {
            if (b.g(new sq(ff, rr)) != null) return false;
            ff += sf; rr += sr;
        }
        return true;
    }

    boolean ap(mv m) {
        pc a = b.g(m.f);
        if (a == null) return false;
        ep = null;
        if (m.ck || m.cq) {
            if (ic(a.o)) return false;
            Chess.Player opp = (a.o == Chess.Player.white) ? Chess.Player.black : Chess.Player.white;
            sq mid = new sq((m.f.f + m.t.f) / 2, m.f.r);
            if (ia(opp, mid) || ia(opp, m.t)) return false;
            if (a.o == Chess.Player.white) {
                wkm = true;
                if (m.ck) {
                    if (b.g(new sq(7,0)) == null) return false;
                    b.mv(m.f, m.t);
                    b.mv(new sq(7,0), new sq(5,0));
                    whr = true;
                } else {
                    if (b.g(new sq(0,0)) == null) return false;
                    b.mv(m.f, m.t);
                    b.mv(new sq(0,0), new sq(3,0));
                    war = true;
                }
            } else {
                bkm = true;
                if (m.ck) {
                    if (b.g(new sq(7,7)) == null) return false;
                    b.mv(m.f, m.t);
                    b.mv(new sq(7,7), new sq(5,7));
                    bhr = true;
                } else {
                    if (b.g(new sq(0,7)) == null) return false;
                    b.mv(m.f, m.t);
                    b.mv(new sq(0,7), new sq(3,7));
                    bar = true;
                }
            }
            tn = (tn == Chess.Player.white) ? Chess.Player.black : Chess.Player.white;
            return true;
        }
        if (m.ep && (a.t == ReturnPiece.PieceType.WP || a.t == ReturnPiece.PieceType.BP)) {
            int dir = (a.o == Chess.Player.white) ? 1 : -1;
            b.s(new sq(m.t.f, m.t.r - dir), null);
        }
        pc cap = b.g(m.t);
        if (a.t == ReturnPiece.PieceType.WK) wkm = true;
        if (a.t == ReturnPiece.PieceType.BK) bkm = true;
        if (a.t == ReturnPiece.PieceType.WR) { if (m.f.f == 0 && m.f.r == 0) war = true; if (m.f.f == 7 && m.f.r == 0) whr = true; }
        if (a.t == ReturnPiece.PieceType.BR) { if (m.f.f == 0 && m.f.r == 7) bar = true; if (m.f.f == 7 && m.f.r == 7) bhr = true; }
        if (cap != null) {
            if (cap.t == ReturnPiece.PieceType.WR) { if (m.t.f == 0 && m.t.r == 0) war = true; if (m.t.f == 7 && m.t.r == 0) whr = true; }
            if (cap.t == ReturnPiece.PieceType.BR) { if (m.t.f == 0 && m.t.r == 7) bar = true; if (m.t.f == 7 && m.t.r == 7) bhr = true; }
        }
        if (a.t == ReturnPiece.PieceType.WP || a.t == ReturnPiece.PieceType.BP) {
            int dr = m.t.r - m.f.r;
            if (Math.abs(dr) == 2) ep = new sq(m.f.f, (m.f.r + m.t.r) / 2);
        }
        b.mv(m.f, m.t);
        if ((a.t == ReturnPiece.PieceType.WP || a.t == ReturnPiece.PieceType.BP) && m.p != null) {
            b.s(m.t, new pc(a.o, m.p));
        }
        tn = (tn == Chess.Player.white) ? Chess.Player.black : Chess.Player.white;
        return true;
    }
}