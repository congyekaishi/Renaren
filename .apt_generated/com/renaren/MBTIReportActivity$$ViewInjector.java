// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MBTIReportActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.MBTIReportActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
    view = finder.findRequiredView(source, 2131361834, "field 'main_body'");
    target.main_body = (android.widget.LinearLayout) view;
  }

  public static void reset(com.renaren.MBTIReportActivity target) {
    target.mTitleBarView = null;
    target.main_body = null;
  }
}
