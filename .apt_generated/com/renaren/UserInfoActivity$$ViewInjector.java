// Generated code from Butter Knife. Do not modify!
package com.renaren;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class UserInfoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.renaren.UserInfoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361883, "field 'tvCompany'");
    target.tvCompany = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361792, "field 'mTitleBarView'");
    target.mTitleBarView = (com.renaren.view.TitleBarView) view;
    view = finder.findRequiredView(source, 2131361881, "field 'tvEmail'");
    target.tvEmail = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361885, "field 'tvWorkExperience'");
    target.tvWorkExperience = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361880, "field 'tvAddress'");
    target.tvAddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361884, "field 'tvPosition'");
    target.tvPosition = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361877, "field 'tvGender'");
    target.tvGender = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361879, "field 'tvIdNum'");
    target.tvIdNum = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361882, "field 'tvRealName'");
    target.tvRealName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361878, "field 'tvBirth'");
    target.tvBirth = (android.widget.TextView) view;
  }

  public static void reset(com.renaren.UserInfoActivity target) {
    target.tvCompany = null;
    target.mTitleBarView = null;
    target.tvEmail = null;
    target.tvWorkExperience = null;
    target.tvAddress = null;
    target.tvPosition = null;
    target.tvGender = null;
    target.tvIdNum = null;
    target.tvRealName = null;
    target.tvBirth = null;
  }
}
