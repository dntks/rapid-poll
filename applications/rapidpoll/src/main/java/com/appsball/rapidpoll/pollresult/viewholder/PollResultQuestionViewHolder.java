package com.appsball.rapidpoll.pollresult.viewholder;

import android.graphics.Color;
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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class PollResultQuestionViewHolder extends PollResultViewHolderParent {

    private PollResultQuestionItemClickListener pollResultQuestionItemClickListener;
    private TextView textView;
    private LinearLayout answersLayout;
    private PieChart pieChart;
    private LayoutInflater layoutInflater;

    public PollResultQuestionViewHolder(View parent, PollResultQuestionItemClickListener pollResultQuestionItemClickListener) {
        super(parent);
        layoutInflater = LayoutInflater.from(parent.getContext());
        textView = (TextView) itemView.findViewById(R.id.question_textview);
        this.pollResultQuestionItemClickListener = pollResultQuestionItemClickListener;
        pieChart = (PieChart) itemView.findViewById(R.id.pie_chart);
        answersLayout = (LinearLayout) itemView.findViewById(R.id.answers_container);
    }

    @Override
    public void bindView(PollResultListItem pollResultListItem) {
        final PollResultQuestionItem pollResultQuestionItem = (PollResultQuestionItem) pollResultListItem;
        textView.setText(pollResultQuestionItem.questionName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pollResultQuestionItemClickListener.onPollResultQuestionItemClicked(pollResultQuestionItem);
            }
        });

        setupAnswerViews(pollResultQuestionItem);
        setupPieChart(pollResultQuestionItem);
    }

    private void setupAnswerViews(PollResultQuestionItem pollResultQuestionItem) {
        int i = 1;
        for (PollResultAnswer resultAnswer : pollResultQuestionItem.alternatives) {
            String alternativeName = resultAnswer.name;
            String percentageString = resultAnswer.getPercentageString();
            View answerRow = layoutInflater.inflate(R.layout.poll_result_answer, null);
            answersLayout.addView(answerRow);
            TextView nameTextView = (TextView) answerRow.findViewById(R.id.answer_textview);
            TextView percentageTextView = (TextView) answerRow.findViewById(R.id.percentage_textview);
            nameTextView.setText(i + ".) " + alternativeName);
            percentageTextView.setText(percentageString);
            i++;
        }
    }

    private void setupPieChart(PollResultQuestionItem pollResultQuestionItem) {
        List<PollResultAnswer> resultAnswers = pollResultQuestionItem.alternatives;
        List<Entry> pieEntries = Lists.newArrayList();
        List<String> pieEntryNames = Lists.newArrayList();
        for (int i = 0; i < resultAnswers.size(); i++) {
            PollResultAnswer resultAnswer = resultAnswers.get(i);
            pieEntries.add(new Entry(i, i));
            pieEntryNames.add(resultAnswer.name);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Answers");
        PieData pieData = new PieData(pieEntryNames, pieDataSet);
//        pieChart.setData(pieData);
        pieChart.setHoleRadius(0);
       pieChart.setUsePercentValues(true);
       pieChart.setDescription("");
       pieChart.setExtraOffsets(5, 10, 5, 5);

       pieChart.setDragDecelerationFrictionCoef(0.95f);

       pieChart.setDrawHoleEnabled(false);
       pieChart.setHoleColorTransparent(true);

       pieChart.setTransparentCircleColor(Color.TRANSPARENT);
       pieChart.setTransparentCircleAlpha(0);

       pieChart.setHoleRadius(58f);
       pieChart.setTransparentCircleRadius(61f);

       pieChart.setDrawCenterText(true);
        setData(3,5, pieChart);
    }
    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    private void setData(int count, float range, PieChart mChart) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}