//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  商品決済1件分に相当する商品販売の集合を表すクラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SalesList extends AbstractTableModel {
	/* 内部データ */
	// 決済対象商品販売リスト
	private ArrayList<Sale> salesList;

	/*
	 * コンストラクタ。
	 */
	public SalesList() {
		salesList = new ArrayList<Sale>();
	}

	/*
	 * 当該決済対象商品販売リストを空にする。
	 */
	public void clear() {
		salesList.clear();
	}

	/*
	 * 当該決済対象商品販売リスト内の商品販売の件数を返す。
	 */
	public int getNumOfSales() {
		return salesList.size();
	}

	/*
	 * 当該決済対象商品販売リストの ith 件目の商品販売を取得する。
	 */
	public Sale getIthSale(int ith) {
		return salesList.get(ith);
	}

	/*
	 * 当該決済対象商品販売リスト内の商品コード code の商品の販売を検索する。見
	 * つかった場合は当該販売リストにおける位置を，見つからなかった場合は -1 を
	 * 返す。
	 */
	public int find(String code) {
		for (int idx = 0; idx < getNumOfSales(); idx++) {
			if (salesList.get(idx).getArticleCode().equals(code)) {
				return idx;
			}
		}
		return -1;
	}

	/*
	 * 当該決済対象商品販売リストに商品販売 sale を追加する。
	 */
	public void add(Sale sale) {
		// 当該決済対象商品販売リストに追加済みの商品販売でない場合のみ追加する。
		if (find(sale.getArticleCode()) < 0) {
			salesList.add(sale);
		}
	}

	/*
	 * 当該決済対象商品販売リストの商品販売の合計金額を返す。
	 */
	public int getTotalPrice() {
		int totalPrice;

		totalPrice = 0;
		for (int idx = 0; idx < getNumOfSales(); idx++) {
			totalPrice += salesList.get(idx).getSubTotalPrice();
		}
		return totalPrice;
	}

	/**************************************************************************
	 * JTable とのインターフェースために使用されるメソッド。
	 * いずれも AbstractTableModel で定義されたものをオーバーライドしている。
	 *************************************************************************/
	/*
	 * テーブルの行数を返す。
	 */
	@Override
	public int getRowCount() {
		return getNumOfSales();
	}

	/*
	 * テーブルの桁数を返す。
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}

	/*
	 * テーブルの桁の名前を返す。
	 */
	@Override
	public String getColumnName(int columnIndex) {
		final String[] columnName = new String[] {
			"購入日",
			"商品コード",
			"商品名",
			"販売単価",
			"販売個数",
			"小計",
		};
		return columnName[columnIndex];
	}

	/*
	 * テーブルの桁の型を返す。
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		final Class<?>[] columnClass = new Class<?>[] {
			String.class,
			String.class,
			String.class,
			Integer.class,
			Integer.class,
			Integer.class,
		};
		return columnClass[columnIndex];
	}

	/*
	 * テーブルが編集可能か否かを返す。
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/*
	 * テーブルの rowIndex 行 columnIndex 桁のデータを返す。
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object ret = null;
		switch (columnIndex) {
		case 0:
			ret = salesList.get(rowIndex).getSalesDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
			break;
		case 1:
			ret = salesList.get(rowIndex).getArticleCode();
			break;
		case 2:
			ret = salesList.get(rowIndex).getArticleName();
			break;
		case 3:
			ret = salesList.get(rowIndex).getSalesPrice();
			break;
		case 4:
			ret = salesList.get(rowIndex).getSalesQuantity();
			break;
		case 5:
			ret = salesList.get(rowIndex).getSubTotalPrice();
			break;
		}
		return ret;
	}

	/*
	 * テーブルの rowIndex 行 columnIndex 桁のデータを aValue に設定する。
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			salesList.get(rowIndex).setSalesDate((ZonedDateTime)aValue);
			break;
		case 1:
			salesList.get(rowIndex).setArticleCode((String)aValue);
			break;
		case 2:
			salesList.get(rowIndex).setArticleName((String)aValue);
			break;
		case 3:
			salesList.get(rowIndex).setSalesPrice((int)aValue);
			break;
		case 4:
			salesList.get(rowIndex).setSalesQuantity((int)aValue);
			break;
		case 5:
			break;
		}
	}
}
