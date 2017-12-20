//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  会員管理画面を実現するクラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import jp.ac.fukuoka_u.tl.NanakumaPOS.Member.Gender;

public class MemberManagementScreenPanel extends JPanel implements ActionListener {
	/* 会員管理画面の状態 */
	enum MemberManagementScreenPanelState {
		NoOperation,	// 何もしていない状態
		Registering,	// 会員管理業務対象の会員の情報を登録している状態
		Showing,		// 会員管理業務対象の会員の情報を表示している状態
		Updating,		// 会員管理業務対象の会員の情報を更新している状態
	};


	/* 関連先 */
	// POS端末全体制御
	private POSTerminalApp app;

	/* 内部データ */
	// 会員管理画面の状態
	private MemberManagementScreenPanelState state;
	// 会員検索画面のフレーム
	private JFrame frame;

	/* ウィジェット */
	// 会員番号ラベル
	private JLabel memberIDLabel;
	// 会員番号欄
	private JTextField memberIDField;
	// 会員氏名ラベル
	private JLabel memberNameLabel;
	// 会員氏名欄
	private JTextField memberNameField;
	// フリガナラベル
	private JLabel memberFuriganaLabel;
	// フリガナ欄
	private JTextField memberFuriganaField;
	// 性別ラベル
	private JLabel memberGenderLabel;
	// 性別グループ
	private ButtonGroup memberGenderGroup;
	// 男性ラジオボタン
	private JRadioButton memberGenderMaleRadioButton;
	// 女性ラジオボタン
	private JRadioButton memberGenderFemaleRadioButton;
	// 実行ボタン
	private JButton okButton;
	// 中止ボタン
	private JButton cancelButton;
	// 会員検索ボタン
	private JButton findMemberButton;
	// 会員登録ボタン
	private JButton registerMemberButton;
	// 会員更新ボタン
	private JButton updateMemberButton;
	// 会員削除ボタン
	private JButton deleteMemberButton;
	// ホーム画面ボタン
	private JButton homeButton;

	/*
	 * コンストラクタ。商品チェック画面が保有するオブジェクトを生成する。
	 */
	public MemberManagementScreenPanel(POSTerminalApp _app) {
		// 関連先を初期化する。
		app = _app;

		// 内部データを初期化する。
		state = MemberManagementScreenPanelState.NoOperation;
		frame = (JFrame)SwingUtilities.getRoot(this);

		// 自身を初期化する。
		setLayout(null);

		// 会員番号入力欄を生成する。
		memberIDLabel = new JLabel("会員番号");
		memberIDLabel.setBounds(16, 16, 100, 24);
		add(memberIDLabel);
		memberIDField = new JTextField(8);
		memberIDField.setBounds(116, 16, 200, 24);
		memberIDField.setBackground(Color.YELLOW);
		add(memberIDField);

		// 会員氏名入力欄を生成する。
		memberNameLabel = new JLabel("会員氏名");
		memberNameLabel.setBounds(16, 48, 100, 24);
		add(memberNameLabel);
		memberNameField = new JTextField(8);
		memberNameField.setBounds(116, 48, 200, 24);
		add(memberNameField);

		// フリガナ入力欄を生成する。
		memberFuriganaLabel = new JLabel("フリガナ");
		memberFuriganaLabel.setBounds(16, 80, 100, 24);
		add(memberFuriganaLabel);
		memberFuriganaField = new JTextField(8);
		memberFuriganaField.setBounds(116, 80, 200, 24);
		add(memberFuriganaField);

		// 性別入力欄を生成する。
		memberGenderLabel = new JLabel("性別");
		memberGenderLabel.setBounds(16, 112, 100, 24);
		add(memberGenderLabel);
		memberGenderGroup = new ButtonGroup();
		memberGenderMaleRadioButton = new JRadioButton("男性");
		memberGenderMaleRadioButton.setBounds(116, 112, 100, 24);
		memberGenderMaleRadioButton.setSelected(true);
		add(memberGenderMaleRadioButton);
		memberGenderFemaleRadioButton = new JRadioButton("女性");
		memberGenderFemaleRadioButton.setBounds(216, 112, 100, 24);
		add(memberGenderFemaleRadioButton);
		memberGenderGroup.add(memberGenderMaleRadioButton);
		memberGenderGroup.add(memberGenderFemaleRadioButton);

		// 実行ボタンを生成する。
		okButton = new JButton("実行");
		okButton.setBounds(116, 144, 80, 24);
		okButton.addActionListener(this);
		okButton.setActionCommand("ok");
		add(okButton);

		// 中止ボタンを生成する。
		cancelButton = new JButton("中止");
		cancelButton.setBounds(216, 144, 80, 24);
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		add(cancelButton);

		// 会員検索ボタンを生成する。
		findMemberButton = new JButton("会員検索");
		findMemberButton.setBounds(832, 16, 160, 48);
		findMemberButton.addActionListener(this);
		findMemberButton.setActionCommand("findMember");
		add(findMemberButton);

		// 会員登録ボタンを生成する。
		registerMemberButton = new JButton("会員登録");
		registerMemberButton.setBounds(832, 80, 160, 48);
		registerMemberButton.addActionListener(this);
		registerMemberButton.setActionCommand("registerMember");
		add(registerMemberButton);

		// 会員更新ボタンを生成する。
		updateMemberButton = new JButton("会員更新");
		updateMemberButton.setBounds(832, 144, 160, 48);
		updateMemberButton.addActionListener(this);
		updateMemberButton.setActionCommand("updateMember");
		add(updateMemberButton);

		// 会員削除ボタンを生成する。
		deleteMemberButton = new JButton("会員削除");
		deleteMemberButton.setBounds(832, 208, 160, 48);
		deleteMemberButton.addActionListener(this);
		deleteMemberButton.setActionCommand("deleteMember");
		add(deleteMemberButton);

		// ホーム画面ボタンを生成する。
		homeButton = new JButton("ホーム画面");
		homeButton.setBounds(832, 272, 160, 48);
		homeButton.addActionListener(this);
		homeButton.setActionCommand("home");
		add(homeButton);

		setState(MemberManagementScreenPanelState.NoOperation);
	}

	/*
	 * 会員管理画面の状態を変更する。
	 */
	public void setState(MemberManagementScreenPanelState _state) {
		state = _state;
		switch (state) {
		case NoOperation:
			memberIDField.setEditable(false);
			memberNameField.setEditable(false);
			memberFuriganaField.setEditable(false);
			memberGenderMaleRadioButton.setEnabled(false);
			memberGenderFemaleRadioButton.setEnabled(false);
			okButton.setEnabled(false);
			cancelButton.setEnabled(false);
			findMemberButton.setEnabled(true);
			registerMemberButton.setEnabled(true);
			updateMemberButton.setEnabled(false);
			deleteMemberButton.setEnabled(false);
			break;
		case Registering:
			memberIDField.setEditable(true);
			memberNameField.setEditable(true);
			memberFuriganaField.setEditable(true);
			memberGenderMaleRadioButton.setEnabled(true);
			memberGenderFemaleRadioButton.setEnabled(true);
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
			findMemberButton.setEnabled(false);
			registerMemberButton.setEnabled(false);
			updateMemberButton.setEnabled(false);
			deleteMemberButton.setEnabled(false);
			break;
		case Showing:
			memberIDField.setEditable(false);
			memberNameField.setEditable(false);
			memberFuriganaField.setEditable(false);
			memberGenderMaleRadioButton.setEnabled(false);
			memberGenderFemaleRadioButton.setEnabled(false);
			okButton.setEnabled(false);
			cancelButton.setEnabled(false);
			findMemberButton.setEnabled(true);
			registerMemberButton.setEnabled(true);
			updateMemberButton.setEnabled(true);
			deleteMemberButton.setEnabled(true);
			break;
		case Updating:
			memberIDField.setEditable(false);
			memberNameField.setEditable(true);
			memberFuriganaField.setEditable(true);
			memberGenderMaleRadioButton.setEnabled(true);
			memberGenderFemaleRadioButton.setEnabled(true);
			okButton.setEnabled(true);
			cancelButton.setEnabled(true);
			findMemberButton.setEnabled(false);
			registerMemberButton.setEnabled(false);
			updateMemberButton.setEnabled(false);
			deleteMemberButton.setEnabled(false);
			break;
		}
	}

	/*
	 * 会員情報の妥当性を検証する。
	 */
	private Boolean validateMemberInfo() {
		if (memberIDField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "会員番号が空欄です。");
			memberIDField.requestFocusInWindow();
			return false;
		}
		if (memberNameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "会員氏名が空欄です。");
			memberNameField.requestFocusInWindow();
			return false;
		}
		if (memberFuriganaField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "フリガナが空欄です。");
			memberFuriganaField.requestFocusInWindow();
			return false;
		}
		return true;
	}

	/*
	 * 会員検索が要求されたときに呼び出される。
	 */
	private void memberFindingRequested() {
		// 店員に会員番号の入力を求める。
		String memberID = JOptionPane.showInputDialog(frame, "会員番号を入力してください。");
		if (memberID != null) {
			// 入力があった場合，会員検索を行う。
			if (app.memberFindingRequested(memberID)) {
				setState(MemberManagementScreenPanelState.Showing);
			} else {
				memberID = JOptionPane.showInputDialog(frame, "会員番号を入力してください。");
			}
		}
	}

	/*
	 * 会員登録が要求されたときに呼び出される。
	 */
	private void memberRegistrationRequested() {
		setState(MemberManagementScreenPanelState.Registering);
		memberIDField.requestFocusInWindow();
		//変更点
		memberIDField.setText(null);
		memberNameField.setText(null);
		memberFuriganaField.setText(null);
	}

	/*
	 * 会員登録が確定されたときに呼び出さされる。
	 */
	private void memberRegistrationConfirmed() {
		Gender gender = Gender.Male;
		Member member = new Member("", "", "", gender,0);
		
		if (JOptionPane.showConfirmDialog(frame,  "会員登録しますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			if (!validateMemberInfo()) {
				return;
			} else {
			//@@@ データベースに会員登録を依頼する部分は未実装。
				if(memberGenderMaleRadioButton.isSelected()) {
					gender = Gender.Male;
				} else if (memberGenderFemaleRadioButton.isSelected()) {
					gender = Gender.Female;
				}
				member = new Member(memberIDField.getText(),memberNameField.getText(),memberFuriganaField.getText(),gender,0);
				if (app.memberRegistrationRequested(member)) {
					setState(MemberManagementScreenPanelState.Showing);
				}
			}
		}
	}

	/*
	 * 会員登録が中止されたときに呼び出される。
	 */
	private void memberRegistrationCancelled() {
		if (JOptionPane.showConfirmDialog(frame, "会員登録を中止しますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

			if(app.getMemberUnderManagement()==null) {
				memberUnderManagementChanged();
				setState(MemberManagementScreenPanelState.NoOperation);
			} else {
				memberUnderManagementChanged();
				setState(MemberManagementScreenPanelState.Showing);
			}
		}
	}

	/*
	 * 会員更新が要求されたときに呼び出される。
	 */
	private void memberUpdatingRequested() {
		setState(MemberManagementScreenPanelState.Updating);
		memberNameField.requestFocusInWindow();
	}

	/*
	 * 会員更新が確定されたときに呼び出される。
	 */
	private void memberUpdatingConfirmed() {
		if (JOptionPane.showConfirmDialog(frame,  "会員情報を更新しますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			if (!validateMemberInfo()) {
				return;
			}
			//@@@ データベースに会員の更新を依頼する部分は未実装。
			Gender gender = Gender.Male;
			if(memberGenderMaleRadioButton.isSelected()) {
				gender = Gender.Male;
			} else if (memberGenderFemaleRadioButton.isSelected()) {
				gender = Gender.Female;
			}
			Member member = new Member(memberIDField.getText(),memberNameField.getText(),memberFuriganaField.getText(),gender,app.getMemberUnderManagement().getPoint());
			if (app.memberUpdatingRequested(member)) {
				setState(MemberManagementScreenPanelState.Showing);
			}
		}
	}

	/*
	 * 会員更新が中止されたときに呼び出される。
	 */
	private void memberUpdatingCancelled() {
		if (JOptionPane.showConfirmDialog(frame, "会員情報の更新を中止しますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			memberUnderManagementChanged();
			setState(MemberManagementScreenPanelState.Showing);

			memberUnderManagementChanged();
		}
	}

	/*
	 * 会員削除が要求されたときに呼び出される。
	 */
	private void memberDeletionRequested() {
		String memberName = app.getMemberUnderManagement().getName();
		if (JOptionPane.showConfirmDialog(frame, "会員「" + memberName + "」を削除しますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			//@@@ データベースに会員の削除を依頼する部分は未実装。
			if (app.memberDeletionRequested(app.getMemberUnderManagement().getID())) {
				setState(MemberManagementScreenPanelState.NoOperation);
			}
		}
	}

	/*
	 * 会員管理のキャンセルが要求されたときに呼び出される。
	 */
	private void memberManagementCancelled() {
		switch (state) {
		case Registering:
		case Updating:
			// 会員情報の登録中または更新中にキャンセルするとそれまでの入力が無
			// 駄になるので，店員に確認が必要である。
			if (JOptionPane.showConfirmDialog(frame, "会員管理をキャンセルしますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				// キャンセル応諾の場合，商品チェックをキャンセルする。
				app.memberManagementCancelled();
			}
			break;
		case NoOperation:
		case Showing:
			app.memberManagementCancelled();
		}
	}

	/*
	 * 会員管理画面上のボタンが押されるときに呼び出される。
	 */
	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("ok")) {
			switch (state) {
			case Registering:
				memberRegistrationConfirmed();
				break;
			case Updating:
				memberUpdatingConfirmed();
				break;
			case NoOperation:
			case Showing:
				// これらの状態では実行ボタンを押せなくしているので，ここが実行
				// されることはない。
				break;
			}
		} else if (cmd.equals("cancel")) {
			switch (state) {
			case Registering:
				memberRegistrationCancelled();
				break;
			case Updating:
				memberUpdatingCancelled();
				break;
			case NoOperation:
			case Showing:
				// これらの状態では中止ボタンを押せなくしているので，ここが実行
				// されることはない。
				break;
			}
		} else if (cmd.equals("findMember")) {
			// 会員検索ボタンが押されたときは会員検索処理を呼び出す。
			memberFindingRequested();
		} else if (cmd.equals("registerMember")) {
			// 会員登録ボタンが押されたときは会員登録処理を呼び出す。
			memberRegistrationRequested();
		} else if (cmd.equals("updateMember")) {
			// 会員更新ボタンが押されたときは会員更新処理を呼び出す。
			memberUpdatingRequested();
		} else if (cmd.equals("deleteMember")) {
			// 会員削除ボタンが押されたときは会員削除処理を呼び出す。
			memberDeletionRequested();
		} else if (cmd.equals("home")) {
			// ホーム画面ボタンが押下されたときは会員管理キャンセル処理を呼び出
			// す。
			memberManagementCancelled();
		}
	}

	/*
	 * 管理対象の会員が更新されたら呼び出される。
	 */
	public void memberUnderManagementChanged() {
		Member member = app.getMemberUnderManagement();
		if (member != null) {
			memberIDField.setText(member.getID());
			memberNameField.setText(member.getName());
			memberFuriganaField.setText(member.getFurigana());
			memberGenderMaleRadioButton.setSelected(member.getGender() == Gender.Male);
			memberGenderFemaleRadioButton.setSelected(member.getGender() == Gender.Female);
		} else {
			memberIDField.setText("");
			memberNameField.setText("");
			memberFuriganaField.setText("");
			memberGenderMaleRadioButton.setSelected(false);
			memberGenderFemaleRadioButton.setSelected(false);
		}
	}
}
