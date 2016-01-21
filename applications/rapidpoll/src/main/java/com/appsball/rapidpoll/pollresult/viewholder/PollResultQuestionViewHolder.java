package com.appsball.rapidpoll.pollresult.viewholder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.pollresult.PollResultQuestionItemClickListener;
import com.appsball.rapidpoll.pollresult.model.PollResultAnswer;
import com.appsball.rapidpoll.pollresult.model.PollResultListItem;
import com.appsball.rapidpoll.pollresult.model.PollResultQuestionItem;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.common.collect.Lists;

import java.util.List;

public class PollResultQuestionViewHolder extends PollResultViewHolderParent {

    private PollResultQuestionItemClickListener pollResultQuestionItemClickListener;
    private TextView questionTextView;
    private TextView noAnswersTextView;
    private LinearLayout answersLayout;
    private PieChart pieChart;
    private LayoutInflater layoutInflater;
    private List<Integer> answerColors;

    public PollResultQuestionViewHolder(View parent,
                                        PollResultQuestionItemClickListener pollResultQuestionItemClickListener,
                                        List<Integer> answerColors) {
        super(parent);
        this.pollResultQuestionItemClickListener = pollResultQuestionItemClickListener;
        this.answerColors = answerColors;
        layoutInflater = LayoutInflater.from(parent.getContext());
        questionTextView = (TextView) itemView.findViewById(R.id.question_textview);
        pieChart = (PieChart) itemView.findViewById(R.id.pie_chart);
        answersLayout = (LinearLayout) itemView.findViewById(R.id.answers_container);
        noAnswersTextView = (TextView) itemView.findViewById(R.id.no_answers_textview);
    }

    @Override
    public void bindView(PollResultListItem pollResultListItem) {
        answersLayout.removeAllViews();
        final PollResultQuestionItem pollResultQuestionItem = (PollResultQuestionItem) pollResultListItem;
        questionTextView.setText(pollResultQuestionItem.questionName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollResultQuestionItemClickListener.onPollResultQuestionItemClicked(pollResultQuestionItem);
            }
        });

        setupResultAnswerView(pollResultQuestionItem.alternatives);
    }

    private void setupResultAnswerView(List<PollResultAnswer> resultAnswers) {
        if (resultAnswers.size() > 0) {
            setupAnswerViews(resultAnswers);
            setupPieChart(resultAnswers);
            hideViewRelatingToHavingAnswers(true);
        } else {
            hideViewRelatingToHavingAnswers(false);
        }
    }

    private void hideViewRelatingToHavingAnswers(boolean questionHasAnswers) {
        noAnswersTextView.setVisibility(questionHasAnswers ? View.GONE : View.VISIBLE);
        answersLayout.setVisibility(questionHasAnswers ? View.VISIBLE : View.GONE);
        pieChart.setVisibility(questionHasAnswers ? View.VISIBLE : View.GONE);
    }

    private void setupAnswerViews(List<PollResultAnswer> resultAnswers) {
        int i = 0;
        for (PollResultAnswer resultAnswer : resultAnswers) {
            String alternativeName = resultAnswer.name;
            String percentageString = resultAnswer.getPercentageString();
            View answerRow = layoutInflater.inflate(R.layout.poll_result_answer, null);
            answersLayout.addView(answerRow);
            TextView nameTextView = (TextView) answerRow.findViewById(R.id.answer_textview);
            TextView percentageTextView = (TextView) answerRow.findViewById(R.id.percentage_textview);
            View colorView = answerRow.findViewById(R.id.color_view);
            percentageTextView.setText(percentageString);
            Integer color = answerColors.get(i);
            setColorView(colorView, color);
            i++;
            nameTextView.setText(i + ".) " + alternativeName);
        }
    }

    private void setColorView(View colorView, Integer color) {
        Drawable background = colorView.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(color);
        }
    }

    private void setupPieChart(List<PollResultAnswer> resultAnswers) {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDrawMarkerViews(false);
        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);
        setData(pieChart, resultAnswers);
    }

    private void setData(PieChart mChart, List<PollResultAnswer> resultAnswers) {
        List<Entry> pieEntries = Lists.newArrayList();
        List<String> pieEntryNames = Lists.newArrayList();
        for (int i = 0; i < resultAnswers.size(); i++) {
            PollResultAnswer resultAnswer = resultAnswers.get(i);
            pieEntries.add(new Entry(resultAnswer.percentageValue, i));
            pieEntryNames.add(resultAnswer.name);
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);
//        dataSet.setVisible(false);
        // add a lot of colors


        dataSet.setColors(answerColors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(pieEntryNames, dataSet);
        data.setDrawValues(false);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

}