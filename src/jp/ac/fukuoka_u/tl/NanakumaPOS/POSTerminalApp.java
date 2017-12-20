//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  POS端末全体制御クラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jp.ac.fukuoka_u.tl.NanakumaPOS.CheckArticlesScreenPanel.CheckArticlesScreenPanelState;
import jp.ac.fukuoka_u.tl.NanakumaPOS.DBServerIF.DBServerIFException;
import jp.ac.fukuoka_u.tl.NanakumaPOS.MemberManagementScreenPanel.MemberManagementScreenPanelState;

public class POSTerminalApp {
	private static POSTerminalApp theApp;

	/* 関連先 */
	// DBサーバ I/F クラスの唯一のインスタンス
	private DBServerIF dbServerIF;
	// カスタマディスプレイ I/F クラスの唯一のインスタンス
	private AbstractedCustomerDisplayIF customerDisplayIF;
	// キャッシュドロワ I/F クラスの唯一のインスタンス
	private AbstractedCashDrawerIF cashDrawerIF;

	/* 内部データ */
	// 商品チェック中の登録会員。会員が確定していない間は null とする。
	private Member memberUnderChecking;
	// 商品チェック中の決済対象商品販売のリスト
	private SalesList salesUnderChecking;
	// 会員管理業務対象の登録会員。会員が確定していない間は null とする。
	private Member memberUnderManagement;

	/* ビュー関係 */
	// ウィンドウフレーム
	private JFrame frame;
	// キャプションラベル
	private JLabel captionLabel;
	// ウィンドウフレームに貼りつくベースのパネル
	private JPanel basePanel;
	// ベースパネル上のウィジェットのレイアウトを司るレイアウトマネージャ
	private CardLayout cardLayout;
	// ホーム画面
	private HomeScreenPanel homeScreenPanel;
	// 商品チェック画面
	private CheckArticlesScreenPanel checkArticlesScreenPanel;
	// 会員登録画面
	private MemberManagementScreenPanel memberManagementScreenPanel;

	/*
	 * プログラムのエントリポイント。この関数からプログラムが開始される。
	 */
	public static void main(String args[]) {
		theApp = new POSTerminalApp ();
	}

	/*
	 * コンストラクタ。
	 */
	private POSTerminalApp() {
		// DBサーバ I/F クラスの唯一のインスタンスを生成する。
		dbServerIF = new DBServerIF();
		// カスタマディスプレイ I/F クラスの唯一のインスタンスを生成する。
		customerDisplayIF = new VirtualCustomerDisplayIF();
		// キャッシュドロワ I/F クラスの唯一のインスタンスを生成する。
		cashDrawerIF = new VirtualCashDrawerIF();

		// 内部データを初期化する。
		memberUnderChecking = null;
		salesUnderChecking = new SalesList();
		memberUnderManagement = null;

		// フレームを生成する。
		frame = new JFrame("ななくま文具店POS端末アプリケーション");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// ラベルを生成する。
		captionLabel = new JLabel("ななくま文具店POSシステム", SwingConstants.CENTER);
		captionLabel.setBounds(0, 0, 1024, 32);
		frame.getContentPane().add(captionLabel);

		// ベースパネルを生成する。
		basePanel = new JPanel();
		basePanel.setBounds(0, 32, 1024, 736);
		frame.getContentPane().add(basePanel);
		cardLayout = new CardLayout();
		basePanel.setLayout(cardLayout);

		// 初期画面を生成する。
		homeScreenPanel = new HomeScreenPanel(this);
		basePanel.add(homeScreenPanel, "HomeScreen");

		// 商品チェック画面を生成する。
		checkArticlesScreenPanel = new CheckArticlesScreenPanel(this);
		basePanel.add(checkArticlesScreenPanel, "CheckArticlesScreen");

		// 会M員登録画面を生成する。
		memberManagementScreenPanel = new MemberManagementScreenPanel(this);
		basePanel.add(memberManagementScreenPanel, "MemberManagementScreen");

		// ホーム画面を選択する。
		cardLayout.show(basePanel, "HomeScreen");

		// ウィンドウを表示する。
		frame.setVisible (true);
	}

	/*
	 * DBサーバインターフェースのインスタンスへの参照を返す。
	 */
	DBServerIF getDBServerIF() {
		return dbServerIF;
	}

	/*
	 * カスタマディスプレイインターフェースのインスタンスへの参照を返す。
	 */
	AbstractedCustomerDisplayIF getCustomerDisplayIF() {
		return customerDisplayIF;
	}

	/*
	 * キャッシュドロワインターフェースのインスタンスへの参照を返す。
	 */
	AbstractedCashDrawerIF getCashDrawerIF() {
		return cashDrawerIF;
	}

	/*
	 * 現在チェックを行っている登録会員への参照を返す。
	 */
	public Member getMemberUnderChecking() {
		return memberUnderChecking;
	}

	/*
	 * チェック中の商品販売リストへの参照を返す。
	 */
	public SalesList getSalesUnderChecking() {
		return salesUnderChecking;
	}

	/*
	 * 管理中の会員購入リストへの参照を返す。
	 */
	public Member getMemberUnderManagement() {
		return memberUnderManagement;
	}

	/**************************************************************************
	 * ユーティリティ関数
	 *************************************************************************/
	/*
	 * 商品販売 sale に関する情報をカスタマディスプレイに表示する。
	 */
	private void displaySalesOnCustomerDisplay(Sale sale) {
		customerDisplayIF.displayUpperMessage(sale.getArticleName(), AbstractedCustomerDisplayIF.Alignment.LEFT);
		String buf = "@" + Integer.toString(sale.getSalesPrice()) + "x" + Integer.toString(sale.getSalesQuantity());
		customerDisplayIF.displayLowerMessage(buf, AbstractedCustomerDisplayIF.Alignment.RIGHT);
	}


	/**************************************************************************
	 * ホーム画面からの要求処理
	 *************************************************************************/
	/*
	 * 商品チェックが要求された場合に呼び出される。
	 */
	public Boolean checkArticlesRequested() {
		// 商品チェック中のユーザが選択されていない状態にする。
		memberUnderChecking = null;
		// 決済対象商品販売リストをクリアする。
		salesUnderChecking.clear();
		salesUnderChecking.fireTableDataChanged();
		// 商品チェック画面を選択する。
		cardLayout.show(basePanel, "CheckArticlesScreen");
		// 商品チェック画面の状態を初期化済み状態にする。
		checkArticlesScreenPanel.setState(CheckArticlesScreenPanelState.Initialized);
		return true;
	}

	/*
	 * 会員管理が要求された場合に呼び出される。
	 */
	public Boolean memberManagementRequested() {
		// 会員管理業務対象の登録会員が未確定の状態にする。
		memberUnderManagement = null;
		memberManagementScreenPanel.memberUnderManagementChanged();
		// 会員管理画面の状態を何もしていない状態にする。
		memberManagementScreenPanel.setState(MemberManagementScreenPanelState.Showing);
		// 会員管理画面を選択する。
		cardLayout.show(basePanel,  "MemberManagementScreen");
		return true;
	}

	/**************************************************************************
	 * 商品チェック画面からの要求処理
	 *************************************************************************/
	/*
	 * 商品コードが入力された場合に呼び出される。
	 */
	public Boolean articleCodeEntered(String articleCode) {
		int idx;
		Article article;
		Sale sale;

		// 決済対象商品販売のリストに当該コードの商品の販売が含まれていないか検
		// 査する。
		idx = salesUnderChecking.find(articleCode);

		// 含まれている場合，
		if (idx >= 0) {
			// 商品販売表中当該商品販売にフォーカスをあてる。
			checkArticlesScreenPanel.focusSales(idx);
			// チェック済みの商品であることを店員に知らせる。
			JOptionPane.showMessageDialog(frame,  "すでにチェックされている商品です。販売個数を確認してください。", "注意", JOptionPane.WARNING_MESSAGE);
			// 商品チェック画面の商品コード入力欄をクリアしフォーカスをあてる。
			checkArticlesScreenPanel.clearArticleCodeField();
			return false;
		}

		// 含まれていない場合，
		try {
			// 商品の名称と定価をデータベースから検索する。
			article = dbServerIF.findArticle(articleCode);
			// 当該商品販売を決済対象商品販売リストに追加する。
			sale = new Sale(article.getArticleCode(), article.getArticleName(), article.getCataloguePrice(), 1);
			salesUnderChecking.add(sale);
			idx = salesUnderChecking.getNumOfSales() - 1;
			salesUnderChecking.fireTableRowsInserted(idx, idx);
			// 商品チェック画面を商品チェック中状態にする。
			checkArticlesScreenPanel.setState(CheckArticlesScreenPanelState.CheckingArticles);
			// 商品販売表中の当該商品販売にフォーカスをあてる。
			checkArticlesScreenPanel.focusSales(idx);
			// カスタマディスプレイに当該商品販売を表示する。
			displaySalesOnCustomerDisplay(sale);
			// 商品チェック画面の商品コード入力欄をクリアしフォーカスをあてる。
			checkArticlesScreenPanel.clearArticleCodeField();
		}
		catch (DBServerIFException ex) {
			// データベースそのものに問題がある場合，
			// またはデータベースのアクセスに問題がある場合，
			// 商品販売表中のすべての商品販売についてフォーカスをはずす。
			checkArticlesScreenPanel.unfocusSales();
			// 問題の発生を店員に知らせる。
			JOptionPane.showMessageDialog(frame, ex.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/*
	 * 会員番号が入力されたときに呼び出される。
	 */
	public Boolean memberIDEntered(String memberID) {
		try {
			memberUnderChecking = dbServerIF.findMember(memberID);
			checkArticlesScreenPanel.memberUnderCheckingChanged();
		}
		catch (DBServerIFException ex) {
			// 問題の発生を店員に知らせる。
			JOptionPane.showMessageDialog(frame, ex.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/*
	 * 販売単価の変更が要求された場合に呼び出される。販売単価を変更する商品販売
	 * の決済対象商品販売リストにおけるインデックスが salesIndex に，入力された
	 * 販売単価が salesPrice に指定される。
	 */
	public Boolean changeSalesPriceRequested(int salesIndex, int salesPrice) {
		// 決済対象商品販売リストを更新する。
		salesUnderChecking.getIthSale(salesIndex).setSalesPrice(salesPrice);
		salesUnderChecking.fireTableRowsUpdated(salesIndex, salesIndex);
		// カスタマディスプレイに表示する。
		displaySalesOnCustomerDisplay(salesUnderChecking.getIthSale(salesIndex));
		return true;
	}

	/*
	 * 販売個数の変更が要求された場合に呼び出される。販売個数を変更する商品販売
	 * の決済対象商品販売リストにおけるインデックスが salesIndex に，入力された
	 * 販売個数が salesQuantity に指定される。
	 */
	public Boolean changeSalesQuantityRequested(int salesIndex, int salesQuantity) {
		// 決済対象商品販売リストを更新する。
		salesUnderChecking.getIthSale(salesIndex).setSalesQuantity(salesQuantity);
		salesUnderChecking.fireTableRowsUpdated(salesIndex, salesIndex);
		// カスタマディスプレイに表示する。
		displaySalesOnCustomerDisplay(salesUnderChecking.getIthSale(salesIndex));
		return true;
	}

	/*
	 * 決済が要求された場合に呼び出される。
	 */
	public Boolean paymentRequested() {
		// 決済対象商品販売の合計金額を得る。
		int totalPrice = salesUnderChecking.getTotalPrice();

	
		
		// カスタマディスプレイに合計金額を表示する。
		customerDisplayIF.displayUpperMessage("合計金額", AbstractedCustomerDisplayIF.Alignment.LEFT);
		customerDisplayIF.displayLowerMessage(Integer.toString(totalPrice), AbstractedCustomerDisplayIF.Alignment.RIGHT);

		// お預かりの入力を求める。
		PaymentDialog paymentDialog = new PaymentDialog(frame, totalPrice);
		paymentDialog.setVisible(true);

		// お預かりの入力がキャンセルされた場合は決済もキャンセルする。
		if (!paymentDialog.isConfirmed())
			return false;

		// お預かり額を得る。
		int paidPrice = paymentDialog.getPaidPrice();

		// おつりを計算する。
		int changePrice = paidPrice - totalPrice;

		// お預かり額とおつりを商品チェック画面に表示する。
		checkArticlesScreenPanel.setPaidPrice(paidPrice);
		checkArticlesScreenPanel.setChangePrice(changePrice);

		// カスタマディスプレイにおつりを表示する。
		customerDisplayIF.displayUpperMessage("おつり", AbstractedCustomerDisplayIF.Alignment.LEFT);
		customerDisplayIF.displayLowerMessage(Integer.toString(changePrice), AbstractedCustomerDisplayIF.Alignment.RIGHT);

		// キャッシュドロワを開ける。
		cashDrawerIF.openDrawer();

		// データベースを更新する。
		//@@@ 未実装

		// 商品チェック画面を決済済み状態にする。
		checkArticlesScreenPanel.setState(CheckArticlesScreenPanelState.PaymentFinished);
		return true;
	}

	/*
	 * 商品チェックのキャンセルが要求された場合に呼び出される。
	 */
	public Boolean checkArticlesCancelled() {
		// ホーム画面を選択する。
		cardLayout.show(basePanel, "HomeScreen");
		// カスタマディスプレイの表示をクリアする。
		customerDisplayIF.clear();
		return true;
	}

	/**************************************************************************
	 * 会員管理画面からの要求処理
	 *************************************************************************/
	/*
	 * 会員検索が要求されたときに呼び出される。
	 */
	public Boolean memberFindingRequested(String memberID) {
		try {
			memberUnderManagement = dbServerIF.findMember(memberID);
			memberManagementScreenPanel.memberUnderManagementChanged();
		}
		catch (DBServerIFException ex) {
			// データベースのアクセスに問題がある場合，問題の発生を店員に知らせ
			// る。
			JOptionPane.showMessageDialog(frame, ex.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/*
	 * 会員登録が要求されたときに呼び出される。
	 */
	public Boolean memberRegistrationRequested(Member member) {
		try {
			memberUnderManagement = member;
			dbServerIF.registerMember(member);
			memberManagementScreenPanel.memberUnderManagementChanged();
		}
		catch (DBServerIFException ex) {
			// データベースのアクセスに問題がある場合，問題の発生を店員に知らせ
			// る。
			JOptionPane.showMessageDialog(frame, ex.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/*
	 * 会員更新が要求されたときに呼び出される。
	 */
	public Boolean memberUpdatingRequested(Member member) {
		//@@@ データベースに会員更新を依頼する部分は未実装。
		try {
			memberUnderManagement = member;
			dbServerIF.updateMember(member);
			memberManagementScreenPanel.memberUnderManagementChanged();
		}
		catch (DBServerIFException ex) {
			// データベースのアクセスに問題がある場合，問題の発生を店員に知らせ
			// る。
			JOptionPane.showMessageDialog(frame, ex.getMessage(), "エラー", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/*
	 * 会員削除が要求されたときに呼び出される。
	 */
	public Boolean memberDeletionRequested(String memberID) {
		//@@@ データベースに会員削除を依頼する部分は未実装。
		
		memberUnderManagement = null;
		memberManagementScreenPanel.memberUnderManagementChanged();
		return true;
	}

	/*
	 * 会員管理のキャンセルが要求された場合に呼び出される。
	 */
	public Boolean memberManagementCancelled() {
		// ホーム画面を選択する。
		cardLayout.show(basePanel, "HomeScreen");
		return true;
	}
}
