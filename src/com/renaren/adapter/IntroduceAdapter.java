package com.renaren.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.renaren.R;
import com.renaren.beans.MbtiCode;
import com.renaren.tools.ListItemClickHelp;

public class IntroduceAdapter extends BaseAdapter {

	private Context context;
	// private MbtiCode mbtiList;
	ArrayList<MbtiCode> MbtiCodes = new ArrayList<MbtiCode>();
	private LayoutInflater inflater;
	private ListItemClickHelp callback;

	public IntroduceAdapter(Context context, ArrayList<MbtiCode> MbtiCodes,
			ListItemClickHelp callback) {

		this.context = context;
		this.MbtiCodes = MbtiCodes;
		this.callback = callback;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MbtiCodes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return MbtiCodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.mbti_adapter, parent, false);
			viewHolder.stateImage = (ImageView) convertView
					.findViewById(R.id.mbtiState);
			viewHolder.mbtiStatus = (LinearLayout) convertView
					.findViewById(R.id.mbtiStateLayout);
			viewHolder.statusA = (TextView) convertView
					.findViewById(R.id.statusA);
			viewHolder.statusB = (TextView) convertView
					.findViewById(R.id.statusB);
			viewHolder.statusC = (TextView) convertView
					.findViewById(R.id.statusC);
			viewHolder.statusD = (TextView) convertView
					.findViewById(R.id.statusD);
			viewHolder.mbtiCode = (TextView) convertView
					.findViewById(R.id.mbtiCode);
			viewHolder.testContinue = (TextView) convertView
					.findViewById(R.id.testContinue);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			// resetViewHolder(viewHolder);
		}

		MbtiCode mc = MbtiCodes.get(position); // 不管视图是否重用，都重新设置数据
		viewHolder.mbtiCode.setText(mc.code);
		if (mc.matching.equals("1")) {
			// viewHolder.stateImage.setImageResource(R.drawable.state_d);
			viewHolder.mbtiStatus.setVisibility(View.VISIBLE);
			viewHolder.stateImage.setVisibility(View.GONE);
			viewHolder.statusA.setVisibility(View.VISIBLE);
			viewHolder.statusB.setVisibility(View.VISIBLE);
			viewHolder.statusC.setVisibility(View.VISIBLE);
			viewHolder.statusD.setVisibility(View.VISIBLE);
			viewHolder.statusA.setBackgroundResource(R.color.statusA);
			viewHolder.statusB.setBackgroundResource(R.color.statusB);
			viewHolder.statusC.setBackgroundResource(R.color.statusC);
			viewHolder.statusD.setBackgroundResource(R.color.statusD);
			viewHolder.statusA.setTextColor(Color.WHITE);
			viewHolder.statusB.setTextColor(Color.WHITE);
			viewHolder.statusC.setTextColor(Color.WHITE);
			viewHolder.statusD.setTextColor(Color.WHITE);
//			viewHolder.statusA.setTextSize(22.0f);
			viewHolder.statusA.setText("A");
			viewHolder.statusB.setText("B");
			viewHolder.statusC.setText("C");
			viewHolder.statusD.setText("D");
			viewHolder.testContinue.setText("查看报告");
			viewHolder.testContinue.setBackgroundResource(R.color.red);
		} else if (mc.value.equals("1")) {
			// viewHolder.stateImage.setImageResource(R.drawable.state_c);
			// viewHolder.statusD.setVisibility(View.GONE);
			viewHolder.mbtiStatus.setVisibility(View.VISIBLE);
			viewHolder.stateImage.setVisibility(View.GONE);
			viewHolder.statusA.setVisibility(View.VISIBLE);
			viewHolder.statusB.setVisibility(View.VISIBLE);
			viewHolder.statusC.setVisibility(View.VISIBLE);
			viewHolder.statusD.setVisibility(View.VISIBLE);
			viewHolder.statusA.setBackgroundResource(R.color.statusA);
			viewHolder.statusB.setBackgroundResource(R.color.statusB);
			viewHolder.statusC.setBackgroundResource(R.color.statusC);
			viewHolder.statusD.setBackgroundResource(R.color.whites);
			viewHolder.statusA.setTextColor(Color.WHITE);
			viewHolder.statusB.setTextColor(Color.WHITE);
			viewHolder.statusC.setTextColor(Color.WHITE);
			viewHolder.statusD.setTextColor(Color.WHITE);
//			viewHolder.statusA.setTextSize(22.0f);
			viewHolder.statusA.setText("A");
			viewHolder.statusB.setText("B");
			viewHolder.statusC.setText("C");
			viewHolder.statusD.setText(" ");
			viewHolder.testContinue.setText("继续测评");
			viewHolder.testContinue.setBackgroundResource(R.color.blue);
		} else if (mc.mbti.equals("1")) {
			// viewHolder.stateImage.setImageResource(R.drawable.state_b);
			// viewHolder.statusC.setVisibility(View.GONE);
			// viewHolder.statusD.setVisibility(View.GONE);
			viewHolder.mbtiStatus.setVisibility(View.VISIBLE);
			viewHolder.stateImage.setVisibility(View.GONE);
			viewHolder.statusA.setVisibility(View.VISIBLE);
			viewHolder.statusB.setVisibility(View.VISIBLE);
			viewHolder.statusC.setVisibility(View.VISIBLE);
			viewHolder.statusD.setVisibility(View.VISIBLE);
			viewHolder.statusA.setBackgroundResource(R.color.statusA);
			viewHolder.statusB.setBackgroundResource(R.color.statusB);
			viewHolder.statusC.setBackgroundResource(R.color.whites);
			viewHolder.statusD.setBackgroundResource(R.color.whites);
			viewHolder.statusA.setTextColor(Color.WHITE);
			viewHolder.statusB.setTextColor(Color.WHITE);
			viewHolder.statusC.setTextColor(Color.WHITE);
			viewHolder.statusD.setTextColor(Color.WHITE);
//			viewHolder.statusA.setTextSize(22.0f);
			viewHolder.statusA.setText("A");
			viewHolder.statusB.setText("B");
			viewHolder.statusC.setText(" ");
			viewHolder.statusD.setText(" ");
			viewHolder.testContinue.setText("继续测评");
			viewHolder.testContinue.setBackgroundResource(R.color.blue);
		} else if (mc.psych.equals("1")) {
			// viewHolder.stateImage.setImageResource(R.drawable.state_a);
			viewHolder.mbtiStatus.setVisibility(View.VISIBLE);
			viewHolder.stateImage.setVisibility(View.GONE);
			viewHolder.statusA.setVisibility(View.VISIBLE);
			viewHolder.statusB.setVisibility(View.VISIBLE);
			viewHolder.statusC.setVisibility(View.VISIBLE);
			viewHolder.statusD.setVisibility(View.VISIBLE);
			viewHolder.statusA.setBackgroundResource(R.color.statusA);
			viewHolder.statusB.setBackgroundResource(R.color.whites);
			viewHolder.statusC.setBackgroundResource(R.color.whites);
			viewHolder.statusD.setBackgroundResource(R.color.whites);
			viewHolder.statusA.setTextColor(Color.WHITE);
			viewHolder.statusB.setTextColor(Color.WHITE);
			viewHolder.statusC.setTextColor(Color.WHITE);
			viewHolder.statusD.setTextColor(Color.WHITE);
//			viewHolder.statusA.setTextSize(22.0f);
			viewHolder.statusA.setText("A");
			viewHolder.statusB.setText(" ");
			viewHolder.statusC.setText(" ");
			viewHolder.statusD.setText(" ");
			viewHolder.testContinue.setText("继续测评");
			viewHolder.testContinue.setBackgroundResource(R.color.blue);
		} else {
			// viewHolder.stateImage.setImageResource(R.drawable.state_aa);
			viewHolder.statusA.setVisibility(View.VISIBLE);
			viewHolder.statusB.setVisibility(View.GONE);
			viewHolder.statusC.setVisibility(View.GONE);
			viewHolder.statusD.setVisibility(View.GONE);
			viewHolder.statusA.setBackgroundResource(R.color.blue0);
			viewHolder.statusA.setTextColor(Color.WHITE);
			viewHolder.statusA.setText("A未完成");
//			viewHolder.statusA.setTextSize(14.0f);
			viewHolder.mbtiStatus.setVisibility(View.VISIBLE);
			viewHolder.testContinue.setText("继续测评");
			viewHolder.testContinue.setBackgroundResource(R.color.blue);
		}

		final View view = convertView;
		final int p = position;
		final int oc = viewHolder.testContinue.getId();
		viewHolder.testContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.onClick(view, p, oc);
			}
		});
		return convertView;
	}

	private void resetViewHolder(ViewHolder viewHolder) {

		viewHolder.mbtiStatus.setVisibility(View.GONE);
		viewHolder.stateImage.setVisibility(View.GONE);
		viewHolder.statusA.setVisibility(View.GONE);
		viewHolder.statusB.setVisibility(View.GONE);
		viewHolder.statusC.setVisibility(View.GONE);
		viewHolder.statusD.setVisibility(View.GONE);
		// viewHolder.statusA.setBackgroundResource(0);
		// viewHolder.statusB.setBackgroundResource(0);
		// viewHolder.statusC.setBackgroundResource(0);
		// viewHolder.statusD.setBackgroundResource(0);
		viewHolder.statusA.setTextColor(null);
		viewHolder.statusB.setTextColor(null);
		viewHolder.statusC.setTextColor(null);
		viewHolder.statusD.setTextColor(null);
		viewHolder.statusA.setText(null);
		viewHolder.statusB.setText(null);
		viewHolder.statusC.setText(null);
		viewHolder.statusD.setText(null);
	}

	final class ViewHolder {
		ImageView stateImage;
		TextView mbtiCode, testContinue, statusA, statusB, statusC, statusD;
		LinearLayout mbtiStatus;
	}

}
