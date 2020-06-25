package erqal.mylibrary.util;

import android.R.color;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Hashtable;

public class UyghurCharUtilities {

    private enum Begtype {
        WDBEG, INBEG, NOBEG;
    }

    private static char BPAD = '\u0600'; // Basic region
    private static char BMAX = '\u06FF';
    // private static char EPAD = '\uFB00'; // presentation form region
    // (extented
    // region)
    // private static char EMAX = '\uFEFF';
    private static char CPAD = '\u0400'; // Cyrillic region
    // private static char CMAX = '\u04FF';

    public static char BQUOTE = '\u00AB'; // beginning quote in Uyghur ( >> )
    public static char EQUOTE = '\u00BB'; // ending quote in Uyghur ( << )

    private static char CHEE = '\u0686';
    private static char GHEE = '\u063A';
    private static char NGEE = '\u06AD';
    private static char SHEE = '\u0634';
    private static char SZEE = '\u0698';
    private static char LA = '\uFEFB';
    private static char _LA = '\uFEFC';
    private static char HAMZA = '\u0626';
    // character map for latin based Uyghur writings and its inverse table
    char[] cmap = new char[256];
    char[] cmapinv = new char[256];

    // character map for Cyrillic based Uyghur scripts and its inverse table
    // (mapped to ULY)
    char[] cyrmap = new char[256];
    char[] cyrmapinv = new char[256];
    // returns Uyghur string in presentation form range, input is expected to be
    // in basic range
    Ligatures[] pform = new Ligatures[256];

    private static UyghurCharUtilities utils;
    private Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public UyghurCharUtilities() {
        inti();
    }

    private void inti() {
        int i;
        char ch;

        for (i = 0; i < cmap.length; i++) {
            cmap[i] = '\0';
        }

        for (i = 0; i < cmapinv.length; i++) {
            cmapinv[i] = '\0';
        }

        cmap['A'] = '\u0627';
        cmap['a'] = '\u0627';
        cmap['B'] = '\u0628';
        cmap['b'] = '\u0628';
        cmap['C'] = '\u0643';
        cmap['c'] = '\u0643';
        cmap['D'] = '\u062F';
        cmap['d'] = '\u062F';
        cmap['E'] = '\u06D5';
        cmap['e'] = '\u06D5';
        cmap['F'] = '\u0641';
        cmap['f'] = '\u0641';
        cmap['G'] = '\u06AF';
        cmap['g'] = '\u06AF';
        cmap['H'] = '\u06BE';
        cmap['h'] = '\u06BE';
        cmap['I'] = '\u0649';
        cmap['i'] = '\u0649';
        cmap['J'] = '\u062C';
        cmap['j'] = '\u062C';
        cmap['K'] = '\u0643';
        cmap['k'] = '\u0643';
        cmap['L'] = '\u0644';
        cmap['l'] = '\u0644';
        cmap['M'] = '\u0645';
        cmap['m'] = '\u0645';
        cmap['N'] = '\u0646';
        cmap['n'] = '\u0646';
        cmap['O'] = '\u0648';
        cmap['o'] = '\u0648';
        cmap['P'] = '\u067E';
        cmap['p'] = '\u067E';
        cmap['Q'] = '\u0642';
        cmap['q'] = '\u0642';
        cmap['R'] = '\u0631';
        cmap['r'] = '\u0631';
        cmap['S'] = '\u0633';
        cmap['s'] = '\u0633';
        cmap['T'] = '\u062A';
        cmap['t'] = '\u062A';
        cmap['U'] = '\u06C7';
        cmap['u'] = '\u06C7';
        cmap['V'] = '\u06CB';
        cmap['v'] = '\u06CB';
        cmap['W'] = '\u06CB';
        cmap['w'] = '\u06CB';
        cmap['X'] = '\u062E';
        cmap['x'] = '\u062E';
        cmap['Y'] = '\u064A';
        cmap['y'] = '\u064A';
        cmap['Z'] = '\u0632';
        cmap['z'] = '\u0632';

        cmap['É'] = '\u06D0';
        cmap['é'] = '\u06D0';
        cmap['Ö'] = '\u06C6';
        cmap['ö'] = '\u06C6';
        cmap['Ü'] = '\u06C8';
        cmap['ü'] = '\u06C8';

        // Uyghur punctuation marks
        cmap[';'] = '\u061B';
        cmap['?'] = '\u061F';
        cmap[','] = '\u060C';

        // the inverse of cmap table, to speed up lookups (without wasting much
        // space)
        // we use BPAD for index operations, we would be wasting BPAD many
        // bytes.
        // We could have used a hash table instead, but didn't think it is
        // worthwhile.
        for (i = 0; i < cmapinv.length; i++) {
            ch = cmap[i];
            if (ch != 0) {
                cmapinv[ch - BPAD] = (char) i;
            }
        }

        // For Cyrillic. This maps between ULY and Cyrillic.
        for (i = 0; i < cyrmap.length; i++) {
            cyrmap[i] = '\0';
        }

        for (i = 0; i < cyrmapinv.length; i++) {
            cyrmapinv[i] = '\0';
        }

        cyrmap['А' - CPAD] = cmap['a'];
        cyrmap['а' - CPAD] = cmap['a'];
        cyrmap['Б' - CPAD] = cmap['b'];
        cyrmap['б' - CPAD] = cmap['b'];
        cyrmap['Д' - CPAD] = cmap['d'];
        cyrmap['д' - CPAD] = cmap['d'];
        cyrmap['Ә' - CPAD] = cmap['e'];
        cyrmap['ә' - CPAD] = cmap['e'];
        cyrmap['Ф' - CPAD] = cmap['f'];
        cyrmap['ф' - CPAD] = cmap['f'];
        cyrmap['Г' - CPAD] = cmap['g'];
        cyrmap['г' - CPAD] = cmap['g'];
        cyrmap['Һ' - CPAD] = cmap['h'];
        cyrmap['һ' - CPAD] = cmap['h'];
        cyrmap['И' - CPAD] = cmap['i'];
        cyrmap['и' - CPAD] = cmap['i'];
        cyrmap['Җ' - CPAD] = cmap['j'];
        cyrmap['җ' - CPAD] = cmap['j'];
        cyrmap['К' - CPAD] = cmap['k'];
        cyrmap['к' - CPAD] = cmap['k'];
        cyrmap['Л' - CPAD] = cmap['l'];
        cyrmap['л' - CPAD] = cmap['l'];
        cyrmap['М' - CPAD] = cmap['m'];
        cyrmap['м' - CPAD] = cmap['m'];
        cyrmap['Н' - CPAD] = cmap['n'];
        cyrmap['н' - CPAD] = cmap['n'];
        cyrmap['О' - CPAD] = cmap['o'];
        cyrmap['о' - CPAD] = cmap['o'];
        cyrmap['П' - CPAD] = cmap['p'];
        cyrmap['п' - CPAD] = cmap['p'];
        cyrmap['Қ' - CPAD] = cmap['q'];
        cyrmap['қ' - CPAD] = cmap['q'];
        cyrmap['Р' - CPAD] = cmap['r'];
        cyrmap['р' - CPAD] = cmap['r'];
        cyrmap['С' - CPAD] = cmap['s'];
        cyrmap['с' - CPAD] = cmap['s'];
        cyrmap['Т' - CPAD] = cmap['t'];
        cyrmap['т' - CPAD] = cmap['t'];
        cyrmap['У' - CPAD] = cmap['u'];
        cyrmap['у' - CPAD] = cmap['u'];
        cyrmap['В' - CPAD] = cmap['v'];
        cyrmap['в' - CPAD] = cmap['v'];
        cyrmap['Х' - CPAD] = cmap['x'];
        cyrmap['х' - CPAD] = cmap['x'];
        cyrmap['Й' - CPAD] = cmap['y'];
        cyrmap['й' - CPAD] = cmap['y'];
        cyrmap['З' - CPAD] = cmap['z'];
        cyrmap['з' - CPAD] = cmap['z'];
        cyrmap['е' - CPAD] = cmap['é'];
        cyrmap['Е' - CPAD] = cmap['é'];
        cyrmap['Ө' - CPAD] = cmap['ö'];
        cyrmap['ө' - CPAD] = cmap['ö'];
        cyrmap['Ү' - CPAD] = cmap['ü'];
        cyrmap['ү' - CPAD] = cmap['ü'];
        cyrmap['Ж' - CPAD] = SZEE;
        cyrmap['ж' - CPAD] = SZEE;
        cyrmap['Ғ' - CPAD] = GHEE;
        cyrmap['ғ' - CPAD] = GHEE;
        cyrmap['Ң' - CPAD] = NGEE;
        cyrmap['ң' - CPAD] = NGEE;
        cyrmap['Ч' - CPAD] = CHEE;
        cyrmap['ч' - CPAD] = CHEE;
        cyrmap['Ш' - CPAD] = SHEE;
        cyrmap['ш' - CPAD] = SHEE;

        // the inverse of cyrmap table, to speed up lookups (without wasting
        // much space)
        // we use CPAD for index operations, we would be wasting CPAD many
        // bytes.
        // We could have used a hash table instead, but didn't think it is
        // worthwhile.
        for (i = 0; i < cyrmapinv.length; i++) {
            ch = cyrmap[i];
            if (ch != 0) {
                cyrmapinv[ch - BPAD] = (char) i;
            }
        }

        for (i = 0; i < pform.length; i++) {
            pform[i] = null;
        }

        pform[cmap['a'] - BPAD] = new Ligatures('\uFE8D', '\uFE8D', '\uFE8D',
                '\uFE8E', Begtype.WDBEG);
        pform[cmap['e'] - BPAD] = new Ligatures('\uFEE9', '\uFEE9', '\uFEE9',
                '\uFEEA', Begtype.WDBEG);
        pform[cmap['b'] - BPAD] = new Ligatures('\uFE8F', '\uFE91', '\uFE92',
                '\uFE90', Begtype.NOBEG);
        pform[cmap['p'] - BPAD] = new Ligatures('\uFB56', '\uFB58', '\uFB59',
                '\uFB57', Begtype.NOBEG);
        pform[cmap['t'] - BPAD] = new Ligatures('\uFE95', '\uFE97', '\uFE98',
                '\uFE96', Begtype.NOBEG);
        pform[cmap['j'] - BPAD] = new Ligatures('\uFE9D', '\uFE9F', '\uFEA0',
                '\uFE9E', Begtype.NOBEG);
        pform[CHEE - BPAD] = new Ligatures('\uFB7A', '\uFB7C', '\uFB7D',
                '\uFB7B', Begtype.NOBEG);
        pform[cmap['x'] - BPAD] = new Ligatures('\uFEA5', '\uFEA7', '\uFEA8',
                '\uFEA6', Begtype.NOBEG);
        pform[cmap['d'] - BPAD] = new Ligatures('\uFEA9', '\uFEA9', '\uFEAA',
                '\uFEAA', Begtype.INBEG);
        pform[cmap['r'] - BPAD] = new Ligatures('\uFEAD', '\uFEAD', '\uFEAE',
                '\uFEAE', Begtype.INBEG);
        pform[cmap['z'] - BPAD] = new Ligatures('\uFEAF', '\uFEAF', '\uFEB0',
                '\uFEB0', Begtype.INBEG);
        pform[SZEE - BPAD] = new Ligatures('\uFB8A', '\uFB8A', '\uFB8B',
                '\uFB8B', Begtype.INBEG);
        pform[cmap['s'] - BPAD] = new Ligatures('\uFEB1', '\uFEB3', '\uFEB4',
                '\uFEB2', Begtype.NOBEG);
        pform[SHEE - BPAD] = new Ligatures('\uFEB5', '\uFEB7', '\uFEB8',
                '\uFEB6', Begtype.NOBEG);
        pform[GHEE - BPAD] = new Ligatures('\uFECD', '\uFECF', '\uFED0',
                '\uFECE', Begtype.NOBEG);
        pform[cmap['f'] - BPAD] = new Ligatures('\uFED1', '\uFED3', '\uFED4',
                '\uFED2', Begtype.NOBEG);
        pform[cmap['q'] - BPAD] = new Ligatures('\uFED5', '\uFED7', '\uFED8',
                '\uFED6', Begtype.NOBEG);
        pform[cmap['k'] - BPAD] = new Ligatures('\uFED9', '\uFEDB', '\uFEDC',
                '\uFEDA', Begtype.NOBEG);
        pform[cmap['g'] - BPAD] = new Ligatures('\uFB92', '\uFB94', '\uFB95',
                '\uFB93', Begtype.NOBEG);
        pform[NGEE - BPAD] = new Ligatures('\uFBD3', '\uFBD5', '\uFBD6',
                '\uFBD4', Begtype.NOBEG);
        pform[cmap['l'] - BPAD] = new Ligatures('\uFEDD', '\uFEDF', '\uFEE0',
                '\uFEDE', Begtype.NOBEG);
        pform[cmap['m'] - BPAD] = new Ligatures('\uFEE1', '\uFEE3', '\uFEE4',
                '\uFEE2', Begtype.NOBEG);
        pform[cmap['n'] - BPAD] = new Ligatures('\uFEE5', '\uFEE7', '\uFEE8',
                '\uFEE6', Begtype.NOBEG);
        pform[cmap['h'] - BPAD] = new Ligatures('\uFEEB', '\uFEEB', '\uFEEC',
                '\uFEEC', Begtype.NOBEG);
        // pform[ cmap['h'] - BPAD ] = new Ligatures ( '\uFBAA', '\uFBAA,
        // '\uFBAD, '\uFBAD, Begtype.NOBEG ) ;
        pform[cmap['o'] - BPAD] = new Ligatures('\uFEED', '\uFEED', '\uFEEE',
                '\uFEEE', Begtype.INBEG);
        pform[cmap['u'] - BPAD] = new Ligatures('\uFBD7', '\uFBD7', '\uFBD8',
                '\uFBD8', Begtype.INBEG);
        pform[cmap['ö'] - BPAD] = new Ligatures('\uFBD9', '\uFBD9', '\uFBDA',
                '\uFBDA', Begtype.INBEG);
        pform[cmap['ü'] - BPAD] = new Ligatures('\uFBDB', '\uFBDB', '\uFBDC',
                '\uFBDC', Begtype.INBEG);
        pform[cmap['w'] - BPAD] = new Ligatures('\uFBDE', '\uFBDE', '\uFBDF',
                '\uFBDF', Begtype.INBEG);
        pform[cmap['é'] - BPAD] = new Ligatures('\uFBE4', '\uFBE6', '\uFBE7',
                '\uFBE5', Begtype.NOBEG);
        pform[cmap['i'] - BPAD] = new Ligatures('\uFEEF', '\uFBE8', '\uFBE9',
                '\uFEF0', Begtype.NOBEG);
        pform[cmap['y'] - BPAD] = new Ligatures('\uFEF1', '\uFEF3', '\uFEF4',
                '\uFEF2', Begtype.NOBEG);
        pform[HAMZA - BPAD] = new Ligatures('\uFE8B', '\uFE8B', '\uFE8C',
                '\uFB8C', Begtype.NOBEG);
    }

    public String getUyPFStr(String str, boolean Enforcement) {

        Ligatures lsyn = pform[cmap['l'] - BPAD];
        Ligatures syn, tsyn;
        Begtype bt = Begtype.WDBEG;

        char[] wp = str.toCharArray();
        String pfstr = "";
        int n = str.length();
        int i, j = 0;
        char wc; // current char
        char pfwc = '\0'; // presentation form char
        char prevwc = '\0'; // previous char
        char ppfwc = '\0'; // previous presenation form char

        char[] pfwp = new char[n];

        for (i = 0; i < n; i++) {
            wc = wp[i];
            if (BPAD <= wc && wc < BMAX) {
                syn = pform[wc - BPAD];

                if (syn != null) {
                    // switch (bt) {
                    // case Begtype.WDBEG:
                    // pfwc = syn.iform;
                    // break;
                    // case Begtype.INBEG:
                    // pfwc = syn.iform;
                    // break;
                    // case Begtype.NOBEG:
                    // pfwc = syn.eform;
                    // break;
                    // default:
                    // break;
                    // }
                    if (bt == Begtype.WDBEG || bt == Begtype.INBEG)
                        pfwc = syn.iform;
                    else
                        pfwc = syn.eform;
                    /*
					 * previous letter does not ask for word-beginning form, and
					 * we have to change it to either medial or beginning form,
					 * depending on the previous letter's current form.
					 */
                    // this means the previous letter was a joinable Uyghur
                    // letter
                    if (bt != Begtype.WDBEG) {
                        tsyn = pform[prevwc - BPAD];

                        // special cases for LA and _LA
                        if (ppfwc == lsyn.iform && wc == cmap['a']) {
                            pfwp[j - 1] = LA;
                            bt = Begtype.WDBEG;
                            continue;
                        } else if (ppfwc == lsyn.eform && wc == cmap['a']) {
                            pfwp[j - 1] = _LA;
                            bt = Begtype.WDBEG;
                            continue;
                        }

                        // update previous character
                        if (ppfwc == tsyn.iform) {
                            pfwp[j - 1] = tsyn.bform;
                        } else if (ppfwc == tsyn.eform) {
                            pfwp[j - 1] = tsyn.mform;
                        }
                    }
                    bt = syn.btype; // we will need this in next round
                } else { // a non-Uyghur char in basic range
                    pfwc = wc;
                    bt = Begtype.WDBEG;
                }
            } else { // not in basic Arabic range ( 0x0600-0x06FF )
                pfwc = wc;
                bt = Begtype.WDBEG;
            }

            pfwp[j] = pfwc;
            ppfwc = pfwc; // previous presentation form wide character
            prevwc = wc;
            j++;
        }

        pfstr = new String(pfwp, 0, j);
        if (Enforcement)
            return pfstr;
        return str;
    }

    private class Ligatures {
        public char iform, bform, mform, eform;
        public Begtype btype;

        public Ligatures(char i, char b, char m, char e, Begtype bt) {
            this.iform = i;
            this.bform = b;
            this.mform = m;
            this.eform = e;
            this.btype = bt;
        }
    }

    public static UyghurCharUtilities getUtilities(Context ctx) {
        if (utils == null) {
            utils = new UyghurCharUtilities();
            utils.setmContext(ctx);
        }
        return utils;
    }

    public String GetFontTypeString(String str) {
        str = str.replace("1", "\uFDA5");
        str = str.replace("2", "\uFDA6");
        str = str.replace("3", "\uFDA7");
        str = str.replace("4", "\uFDA8");
        str = str.replace("5", "\uFDA9");
        str = str.replace("6", "\uFDAA");
        str = str.replace("7", "\uFDAB");
        str = str.replace("8", "\uFDAC");
        str = str.replace("9", "\uFDAD");
        str = str.replace("0", "\uFDAE");
        // --------------
        str = str.replace("A", "\uFDAF");
        str = str.replace("B", "\uFDB0");
        str = str.replace("C", "\uFDB1");
        str = str.replace("D", "\uFDB2");
        str = str.replace("E", "\uFDB3");
        str = str.replace("F", "\uFDB4");

        str = str.replace("G", "\uFDB5");
        str = str.replace("H", "\uFDB6");
        str = str.replace("J", "\uFDB7");
        str = str.replace("K", "\uFDB8");
        str = str.replace("L", "\uFDB9");
        str = str.replace("M", "\uFDBA");
        str = str.replace("N", "\uFDBB");
        str = str.replace("V", "\uFDBC");

        str = str.replace("X", "\uFDBD");
        str = str.replace("Z", "\uFDBE");
        str = str.replace("Q", "\uFDBF");
        str = str.replace("W", "\uFDC0");
        str = str.replace("R", "\uFDC1");
        str = str.replace("T", "\uFDC2");
        str = str.replace("Y", "\uFDC3");
        str = str.replace("U", "\uFDC4");

        str = str.replace("I", "\uFDC5");
        str = str.replace("O", "\uFDC6");
        str = str.replace("P", "\uFDC7");
        str = str.replace("S", "\uFDC8");

        // ------------------
        str = str.replace("a", "\uFD20");
        str = str.replace("b", "\uFD21");
        str = str.replace("c", "\uFD22");
        str = str.replace("d", "\uFD23");
        str = str.replace("e", "\uFD24");
        str = str.replace("f", "\uFD25");

        str = str.replace("g", "\uFD26");
        str = str.replace("h", "\uFD27");
        str = str.replace("j", "\uFD28");
        str = str.replace("k", "\uFD29");
        str = str.replace("l", "\uFD2A");
        str = str.replace("m", "\uFD2B");
        str = str.replace("n", "\uFD2C");
        str = str.replace("v", "\uFD2D");

        str = str.replace("x", "\uFD2E");
        str = str.replace("z", "\uFD2F");
        str = str.replace("q", "\uFD30");
        str = str.replace("w", "\uFD31");
        str = str.replace("r", "\uFD32");
        str = str.replace("t", "\uFD33");
        str = str.replace("y", "\uFD34");
        str = str.replace("u", "\uFD35");

        str = str.replace("i", "\uFD36");
        str = str.replace("o", "\uFD37");
        str = str.replace("p", "\uFD38");
        str = str.replace("s", "\uFD39");
        return str;
    }

    public void setSystemViewText(View view, Context context) {
        ViewGroup vGroup;
        if (view instanceof ViewGroup) {
            vGroup = (ViewGroup) view;
            for (int i = 0; i < vGroup.getChildCount(); i++) {
                if (vGroup.getChildAt(i) instanceof TextView) {
                    TextView textView = ((TextView) vGroup.getChildAt(i));

                    textView.setTypeface(getTypeFace(context));
                    textView.setText(setUText(textView.getText().toString(),
                            false));
                    textView.setShadowLayer(1f, 1.2f, 1.2f, color.white);
                    textView.setGravity(Gravity.CENTER);
                    try {
                        if (textView.getClass() == Class
                                .forName("com.android.internal.widget.DialogTitle"))
                            textView.setTextSize(16);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else
                    setSystemViewText(vGroup.getChildAt(i), context);
            }

        }

    }

    public String setUText(String text, boolean Enforcement) {
        if (Enforcement || getSDKVersionNumber() < 16)
            return getUyPFStr(text, Enforcement);
        return text;
    }

    private int getSDKVersionNumber() {
        return android.os.Build.VERSION.SDK_INT;
    }

    // UkijTuzTom.ttf
    // /UKIJEkran.ttf
    private final static String FONT_NANME = "Alp_ekran.ttf";

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface getTypeFace(Context context) {
        Typeface tf = fontCache.get(FONT_NANME);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), FONT_NANME);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(FONT_NANME, tf);
        }
        return tf;
    }

    public static void setTypeFace(TextView v) {
        if (v != null)
            v.setTypeface(getTypeFace(v.getContext()));
    }

    public static void setTypeFace(Button v) {
        if (v != null)
            v.setTypeface(getTypeFace(v.getContext()));
    }

    public static void setTypeFace(EditText v) {
        if (v != null)
            v.setTypeface(getTypeFace(v.getContext()));
    }

    public String getUKYFromUy(String uystr) {
        int j;
        char ch;
        char prev = '\0';
        char[] t = new char[uystr.length() * 2];

        j = 0;
        for (int i = 0; i < uystr.length(); i++) {
            ch = uystr.charAt(i);

            char next = '\0';

            if (i < uystr.length() - 1) {
                next = uystr.charAt(i + 1);
            }

            if (ch == HAMZA) {
                // no hamza necessary in UKY (ULY)
                if (isInUyghurRange(prev) && !isUyVowel(prev)
                        && isInUyghurRange(next)) {
                    t[j++] = '\'';
                }

                continue;
            }

            if (ch == GHEE) {
                if (prev == cmap['n']) {
                    t[j++] = '\'';
                }

                t[j++] = 'g';
                t[j++] = 'h';
            } else if (ch == SZEE) {
                t[j++] = 'j';
            } else if (ch == CHEE) {
                t[j++] = 'c';
                t[j++] = 'h';
            } else if (ch == SHEE) {
                t[j++] = 's';
                t[j++] = 'h';
            } else if (ch == NGEE) {
                t[j++] = 'n';
                t[j++] = 'g';
            } else if (BPAD <= ch && ch < (BPAD + cmapinv.length)
                    && cmapinv[(int) ch - BPAD] != '\0') {
                // put a seperator between characters than can form a joint
                // letter
                if ((prev == cmap['n'] && ch == cmap['g'])
                        || (prev == cmap['s'] && ch == cmap['h'])) {
                    t[j++] = '\'';
                }

                // the following two statements are not necessary. The
                // initialization
                // step for cmapinv points inverse index to lower case letters.
                // string s = cmapinv[(int)ch-BPAD] + "";
                // t[j++] = s.ToLower()[0];

                t[j++] = cmapinv[(int) ch - BPAD];
            } else {
                if (ch == cmap['?']) {
                    t[j++] = '?';
                } else if (ch == cmap[',']) {
                    t[j++] = ',';
                } else if (ch == cmap[';']) {
                    t[j++] = ';';
                } else if (ch == BQUOTE || ch == EQUOTE) {
                    t[j++] = '"';
                } else {
                    t[j++] = ch;
                }
            }

            prev = ch;
        }

        String str = new String(t, 0, j);
        return str;
    }

    public boolean isInUyghurRange(char input) {
        if (input >= 0x0600 && input <= 0x06FF) {
            return true;
        }

        return false;
    }

    public boolean isUyVowel(int ch) {
        if (ch == cmap['a'] || ch == cmap['e'] || ch == cmap['i']
                || ch == cmap['é'] || ch == cmap['o'] || ch == cmap['ö']
                || ch == cmap['u'] || ch == cmap['ü']) {
            return true;
        }

        return false;
    }

}
