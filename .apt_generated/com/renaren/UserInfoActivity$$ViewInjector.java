// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class UserInfoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.UserInfoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361858, "field 'tvAddress'");
    target.tvAddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361856, "field 'tvBirth'");
    target.tvBirth = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361859, "field 'tvEmail'");
    target.tvEmail = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361860, "field 'tvRealName'");
    target.tvRealName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361857, "field 'tvIdNum'");
    target.tvIdNum = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
    view = finder.findRequiredView(source, 2131361861, "field 'tvCompany'");
    target.tvCompany = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361855, "field 'tvGender'");
    target.tvGender = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361863, "field 'tvWorkExperience'");
    target.tvWorkExperience = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361862, "field 'tvPosition'");
    target.tvPosition = (android.widget.TextView) view;
  }

  public static void reset(com.renaren.UserInfoActivity target) {
    target.tvAddress = null;
    target.tvBirth = null;
    target.tvEmail = null;
    target.tvRealName = null;
    target.tvIdNum = null;
    target.mTitleBarView = null;
    target.tvCompany = null;
    target.tvGender = null;
    target.tvWorkExperience = null;
    target.tvPosition = null;
  }
}
