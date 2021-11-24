package com.education.comic.ebook.core.designsystem

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface DsDialog {
    fun showProgressDialog()
    fun hideProgressDialog()
}

fun Completable.dsDialog(dsDialog: DsDialog): Completable =
        this.doOnSubscribe { dsDialog.showProgressDialog() }
        .doOnTerminate { dsDialog.hideProgressDialog() }

fun <T> Single<T>.dsDialog(dsDialog: DsDialog): Single<T> = this
        .doOnSubscribe { dsDialog.showProgressDialog() }
        .doOnTerminate { dsDialog.hideProgressDialog() }

fun <T> Observable<T>.dsDialog(dsDialog: DsDialog): Observable<T> = this
        .doOnSubscribe { dsDialog.showProgressDialog() }
        .doOnTerminate { dsDialog.hideProgressDialog() }

fun <T> Flowable<T>.dsDialog(dsDialog: DsDialog): Flowable<T> = this
        .doOnSubscribe { dsDialog.showProgressDialog() }
        .doOnTerminate { dsDialog.hideProgressDialog() }