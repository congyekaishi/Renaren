// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TrainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.TrainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361838, "field 'main_body'");
    target.main_body = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
  }

  public static void reset(com.renaren.TrainActivity target) {
    target.main_body = null;
    target.mTitleBarView = null;
  }
}
