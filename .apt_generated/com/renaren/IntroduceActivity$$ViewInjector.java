// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class IntroduceActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.IntroduceActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361817, "field 'addCode'");
    target.addCode = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
    view = finder.findRequiredView(source, 2131361816, "field 'etNewCode'");
    target.etNewCode = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361814, "field 'LvMbti'");
    target.LvMbti = (android.widget.ListView) view;
  }

  public static void reset(com.renaren.IntroduceActivity target) {
    target.addCode = null;
    target.mTitleBarView = null;
    target.etNewCode = null;
    target.LvMbti = null;
  }
}
