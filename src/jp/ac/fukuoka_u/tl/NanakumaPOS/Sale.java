//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  商品販売1件分を意味する実体クラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.time.ZonedDateTime;

public class Sale {
	/* 内部データ */
	// 販売日
	private ZonedDateTime salesDate;
	// 商品コード
	private String articleCode;
	// 商品名
	private String articleName;
	// 販売単価
	private int salesPrice;
	// 販売個数
	private int salesQuantity;

	/*
	 * コンストラクタ。商品コード _articleCode，商品名 _articleName，販売単価
	 * _salesPrice，販売個数 _salesQuantity の商品販売1件を生成する。
	 */
	public Sale (String _articleCode, String _articleName, int _salesPrice, int _salesQuantity) {
		salesDate = ZonedDateTime.now();
		articleCode = _articleCode;
		articleName = _articleName;
		salesPrice = _salesPrice;
		salesQuantity = _salesQuantity;
	}

	/*
	 * 当該商品販売の販売日を返す。
	 */
	public ZonedDateTime getSalesDate() {
		return salesDate;
	}

	/*
	 * 当該商品販売の販売日を設定する。
	 */
	public void setSalesDate(ZonedDateTime _salesDate) {
		salesDate = _salesDate;
	}

	/*
	 * 当該商品販売で販売する商品のコードを返す。
	 */
	public String getArticleCode() {
		return articleCode;
	}

	/*
	 * 当該商品販売で販売する商品のコードを設定する。
	 */
	public void setArticleCode(String _articleCode) {
		articleCode = _articleCode;
	}

	/*
	 * 当該商品販売で販売する商品の名前を返す。
	 */
	public String getArticleName() {
		return articleName;
	}

	/*
	 * 当該商品販売で販売する商品の名前を設定する。
	 */
	public void setArticleName(String _articleName) {
		articleName = _articleName;
	}

	/*
	 * 当該商品販売での販売単価を返す。
	 */
	public int getSalesPrice() {
		return salesPrice;
	}

	/*
	 * 当該商品販売での販売単価を設定する。
	 */
	public void setSalesPrice(int _salesPrice) {
		salesPrice = _salesPrice;
	}

	/*
	 * 当該商品販売の販売個数を返す。
	 */
	public int getSalesQuantity() {
		return salesQuantity;
	}

	/*
	 * 当該商品販売の販売個数を設定する。
	 */
	public void setSalesQuantity(int _salesQuantity) {
		salesQuantity = _salesQuantity;
	}

	/*
	 * 当該商品販売の小計を返す。
	 */
	public int getSubTotalPrice() {
		return salesPrice * salesQuantity;
	}
}
