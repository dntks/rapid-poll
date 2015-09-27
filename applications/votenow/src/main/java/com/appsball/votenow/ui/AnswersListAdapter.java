package com.appsball.votenow.ui;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appsball.votenow.R;
import com.appsball.votenow.model.Choice;
import com.appsball.votenow.model.Comment;
import com.appsball.votenow.model.ResultModel;

public class AnswersListAdapter extends BaseAdapter {
	private static final int TYPE_ANSWER = 0;
	private static final int TYPE_USERNAMES = 1;
	private static final int TYPE_COMMENT = 2;
	private static final int TYPE_COMMENTS_HEADER = 3;
	private static final int VIEW_TYPE_COUNT = 4;

	private final ResultModel resultModel;
	private int count;
	// private int[] answerIndexes;
	private List<Integer> answerIndexList;
	private SparseArray<Integer> answersArray;
	private final LayoutInflater layoutInflater;

	public AnswersListAdapter(ResultModel resultModel, LayoutInflater layoutInflater) {
		this.resultModel = resultModel;
		this.layoutInflater = layoutInflater;
		setupCountAndIndexes(resultModel);

	}

	private void setupCountAndIndexes(ResultModel resultModel) {
		int answersSize = resultModel.getChoices().size();
		// answerIndexes = new int[answersSize];
		answerIndexList = new ArrayList<>();
		if (resultModel.isMultiChoice()) {
			count = answersSize + resultModel.countChosenAnswers() + resultModel.getComments().size() + 1;
			answerIndexList.add(0);
			for (int i = 1; i < answersSize; i++) {
				int previousNumber = answerIndexList.get(i - 1);
				int addedNumber = hasComments(resultModel, i - 1) ? 2 : 1;
				answerIndexList.add(addedNumber + previousNumber);
			}
		} else {
			count = answersSize + resultModel.countChosenAnswers();
			answerIndexList.add(0);

			for (int i = 1; i < answersSize; i++) {
				answerIndexList.add(1 + resultModel.getChoices().get(i - 1).getComments().size() + answerIndexList.get(i - 1));
			}
		}
	}

	private boolean hasComments(ResultModel resultModel, int i) {
		return resultModel.getChoices().get(i).getComments().size() > 0;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		if (resultModel.isMultiChoice()) {
			if (position >= count - resultModel.getComments().size()) {
				return TYPE_COMMENT;
			} else if (position == count - resultModel.getComments().size() - 1) {
				return TYPE_COMMENTS_HEADER;
			} else {
				return answerIndexList.contains(position) ? TYPE_ANSWER : TYPE_USERNAMES;
			}
		} else {
			return answerIndexList.contains(position) ? TYPE_ANSWER : TYPE_COMMENT;
		}
	}

	private Comment getCommentForPosition(int position) {
		if (resultModel.isMultiChoice()) {
			int commentPosition = position - (count - resultModel.getComments().size());
			return resultModel.getComments().get(commentPosition);
		} else {
			int indexOfQuestionForComment = 0;
			int chosenPosition = 0;
			for (int i = 0; i < answerIndexList.size(); i++) {
				Integer questionPosition = answerIndexList.get(i);
				if (questionPosition < position) {
					indexOfQuestionForComment = i;
					chosenPosition = questionPosition;
				} else {
					break;
				}

			}
			int locationOfComment = position - chosenPosition - 1;
			return resultModel.getChoices().get(indexOfQuestionForComment).getComments().get(locationOfComment);
		}
	}

	private Choice getAnswerForPosition(int position) {
		for (int i = 0; i < answerIndexList.size(); i++) {
			Integer questionPosition = answerIndexList.get(i);
			if (questionPosition == position) {
				return resultModel.getChoices().get(i);
			}
		}
		return null;
	}

	private String getUserNamesForPosition(int position) {
		for (int i = 0; i < answerIndexList.size(); i++) {
			Integer questionPosition = answerIndexList.get(i);
			if (questionPosition == position - 1) {
				Choice choice = resultModel.getChoices().get(i);
				return choice.createNamesForChoice();
			}
		}
		return "";
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VoteRowViewHolder voteRowViewHolder = null;
		CommentViewHolder commentViewHolder = null;
		UserNamesViewHolder userNamesViewHolder = null;
		int type = getItemViewType(position);
		System.out.println("getView " + position + " " + convertView + " type = " + type);
		if (convertView == null) {
			voteRowViewHolder = new VoteRowViewHolder();
			commentViewHolder = new CommentViewHolder();
			userNamesViewHolder = new UserNamesViewHolder();
			switch (type) {
			case TYPE_USERNAMES:
			case TYPE_COMMENTS_HEADER:
				convertView = layoutInflater.inflate(R.layout.names_row, null);
				userNamesViewHolder.namesText = (TextView) convertView.findViewById(R.id.names_text);
				convertView.setTag(userNamesViewHolder);
				break;
			case TYPE_COMMENT:
				convertView = layoutInflater.inflate(R.layout.comment_row, null);
				commentViewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_text);
				commentViewHolder.commentTextView = (TextView) convertView.findViewById(R.id.comment_text);
				convertView.setTag(commentViewHolder);
				break;
			case TYPE_ANSWER:
				convertView = layoutInflater.inflate(R.layout.voterow, null);
				voteRowViewHolder.answerTextView = (TextView) convertView.findViewById(R.id.answer_text);
				voteRowViewHolder.percentTextView = (TextView) convertView.findViewById(R.id.percent_text);
				voteRowViewHolder.summaTextView = (TextView) convertView.findViewById(R.id.summa_text);
				convertView.setTag(voteRowViewHolder);
				break;
			}
		} else {
			switch (type) {
			case TYPE_USERNAMES:
			case TYPE_COMMENTS_HEADER:
				userNamesViewHolder = (UserNamesViewHolder) convertView.getTag();
				break;
			case TYPE_COMMENT:
				commentViewHolder = (CommentViewHolder) convertView.getTag();
				break;
			case TYPE_ANSWER:
				voteRowViewHolder = (VoteRowViewHolder) convertView.getTag();
				break;
			}
		}
		switch (type) {
		case TYPE_USERNAMES:
			String names = getUserNamesForPosition(position);
			userNamesViewHolder.namesText.setText(names);
			break;
		case TYPE_COMMENTS_HEADER:
			if (resultModel.getComments().size() > 0) {
				userNamesViewHolder.namesText.setText("Comments");
			} else {
				userNamesViewHolder.namesText.setText("");
			}
			break;
		case TYPE_COMMENT:
			Comment comment = getCommentForPosition(position);
			String text = "";
			if (comment != null) {
				if (comment.getName() != null) {
					text = comment.getName() + ": ";
				}
				if (comment.getComment() != null) {
					text += comment.getComment();
				}
			}
			commentViewHolder.nameTextView.setText(text);
			commentViewHolder.commentTextView.setText("");
			break;
		case TYPE_ANSWER:
			Choice choice = getAnswerForPosition(position);
			if (choice != null) {
				voteRowViewHolder.answerTextView.setText(choice.getName());
				voteRowViewHolder.summaTextView.setText("" + choice.getNumber());
				voteRowViewHolder.percentTextView.setText(choice.getPercentage());
			}
			break;
		}
		return convertView;
	}

	public static class VoteRowViewHolder {
		public TextView answerTextView;
		public TextView summaTextView;
		public TextView percentTextView;
	}

	public static class CommentViewHolder {
		public TextView nameTextView;
		public TextView commentTextView;
	}

	public static class UserNamesViewHolder {
		public TextView namesText;
	}
}
