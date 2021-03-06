package com.appsball.rapidpoll.pollresult.viewholder;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsball.rapidpoll.R;
import com.appsball.rapidpoll.commons.model.ResultAlternativeDetails;
import com.appsball.rapidpoll.commons.utils.Utils;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PollResultQuestionViewHolder extends PollResultViewHolderParent {

    private PollResultQuestionItemClickListener pollResultQuestionItemClickListener;
    @BindView(R.id.question_textview)  TextView questionTextView;
    @BindView(R.id.no_answers_textview)  TextView noAnswersTextView;
    @BindView(R.id.people_img)  ImageView peopleImg;
    @BindView(R.id.answers_container)  LinearLayout answersLayout;
    @BindView(R.id.pie_chart)  PieChart pieChart;

    private LayoutInflater layoutInflater;
    private List<Integer> answerColors;
    private boolean isAnonymous;

    public PollResultQuestionViewHolder(View parent,
                                        PollResultQuestionItemClickListener pollResultQuestionItemClickListener,
                                        List<Integer> answerColors,
                                        boolean isAnonymous) {
        super(parent);
        this.pollResultQuestionItemClickListener = pollResultQuestionItemClickListener;
        this.answerColors = answerColors;
        this.isAnonymous = isAnonymous;
        layoutInflater = LayoutInflater.from(parent.getContext());
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(PollResultListItem pollResultListItem) {
        answersLayout.removeAllViews();
        final PollResultQuestionItem pollResultQuestionItem = (PollResultQuestionItem) pollResultListItem;
        questionTextView.setText(pollResultQuestionItem.questionName);
        final List<ResultAlternativeDetails> resultAlternativeDetailsList = setupResultAnswerView(pollResultQuestionItem.alternatives);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollResultQuestionItemClickListener.onPollResultQuestionItemClicked(resultAlternativeDetailsList);
            }
        });
        peopleImg.setVisibility(isAnonymous ? View.GONE : View.VISIBLE);

    }

    private List<ResultAlternativeDetails> setupResultAnswerView(List<PollResultAnswer> resultAnswers) {
        List<ResultAlternativeDetails> resultAlternativeDetailsList = Lists.newArrayList();
        if (resultAnswers.size() > 0) {
            resultAlternativeDetailsList.addAll(setupAnswerViews(resultAnswers));
            setupPieChart(resultAnswers);
            hideViewRelatingToHavingAnswers(true);
        } else {
            hideViewRelatingToHavingAnswers(false);
        }
        return resultAlternativeDetailsList;
    }

    private void hideViewRelatingToHavingAnswers(boolean questionHasAnswers) {
        noAnswersTextView.setVisibility(questionHasAnswers ? View.GONE : View.VISIBLE);
        answersLayout.setVisibility(questionHasAnswers ? View.VISIBLE : View.GONE);
        pieChart.setVisibility(questionHasAnswers ? View.VISIBLE : View.GONE);
    }

    private List<ResultAlternativeDetails> setupAnswerViews(List<PollResultAnswer> resultAnswers) {
        List<ResultAlternativeDetails> resultAlternativeDetailsList = Lists.newArrayList();
        int i = 0;
        for (PollResultAnswer resultAnswer : resultAnswers) {
            ResultAlternativeDetails.Builder builder = ResultAlternativeDetails.builder();
            builder.withNameWithOrder(Utils.getLetterOfAlphabet(i) + ".) " + resultAnswer.name);
            builder.withPercentage(resultAnswer.getPercentageString());
            builder.withAnswerColor(answerColors.get(i%answerColors.size()));
            builder.addEmails(resultAnswer.pollResultEmailList);
            ResultAlternativeDetails resultAlternativeDetails = builder.build();
            setupResultAlternativeView(resultAlternativeDetails);
            resultAlternativeDetailsList.add(resultAlternativeDetails);
            i++;
        }
        return resultAlternativeDetailsList;
    }

    private void setupResultAlternativeView(ResultAlternativeDetails resultAlternativeDetails) {

        View answerRow = layoutInflater.inflate(R.layout.poll_result_answer, null);
        answersLayout.addView(answerRow);
        TextView nameTextView = (TextView) answerRow.findViewById(R.id.answer_textview);
        TextView percentageTextView = (TextView) answerRow.findViewById(R.id.percentage_textview);
        View colorView = answerRow.findViewById(R.id.color_view);
        setColorView(colorView, resultAlternativeDetails.answerColor);
        percentageTextView.setText(resultAlternativeDetails.percentage);
        nameTextView.setText(resultAlternativeDetails.nameWithOrder);
    }

    private void setColorView(View colorView, Integer color) {
        Drawable background = colorView.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(color);
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(color);
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