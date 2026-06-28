package chess;

final class sq {
    final int f; // 0..7
    final int r;
    sq(int f, int r) { this.f = f; this.r = r; }
    static sq ps(String s) {
        int f = s.charAt(0) - 'a';
        int r = s.charAt(1) - '1';
        return new sq(f, r);
    }
    boolean ok() { return f>=0 && f<8 && r>=0 && r<8; }
    @Override public boolean equals(Object o) {
        if (!(o instanceof sq)) return false;
        sq x = (sq)o;
        return f==x.f && r==x.r;
    }
    @Override public int hashCode() { return f*31 + r; }
}