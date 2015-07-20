// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FaceActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.FaceActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
  }

  public static void reset(com.renaren.FaceActivity target) {
    target.mTitleBarView = null;
  }
}
