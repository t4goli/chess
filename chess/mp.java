package chess;

final class mp {
    private mp() {}

    static pr ps(String s) {
        String x = (s == null) ? "" : s.trim();
        if (x.length() == 0) return new pr(new sq(0,0), new sq(0,0), false, null);
        String[] a = x.split("\\s+");
        if (a.length < 2) return new pr(new sq(0,0), new sq(0,0), false, null);
        boolean d = a[a.length - 1].equals("draw?");
        int n = a.length - (d ? 1 : 0);
        if (n < 2) return new pr(new sq(0,0), new sq(0,0), false, null);
        if (a[0].length() < 2 || a[1].length() < 2) return new pr(new sq(0,0), new sq(0,0), false, null);
        sq f = sq.ps(a[0]);
        sq t = sq.ps(a[1]);
        Character pl = null;
        if (n >= 3 && a[2].length() >= 1) pl = a[2].charAt(0);
        return new pr(f, t, d, pl);
    }
}