package Toast;

import java.awt.*;

/**
 * Toast.ToastStatus 枚举类
 *  - INFO #D9EDF7
 *  - ERROR #F2DEDE
 *  - SUCCESS #DFF0D8
 */
public enum ToastStatus
{
    INFO(
            new Color(80, 178, 227),
            Color.black
    ),
    ERROR(
            new Color(238, 105, 92),
            Color.black
    ),
    SUCCESS(
            new Color(116, 239, 66),
            Color.black
    );

    public final Color background;
    public final Color foreground;

    ToastStatus(Color bg, Color fg) {background = bg; foreground = fg;}


}
