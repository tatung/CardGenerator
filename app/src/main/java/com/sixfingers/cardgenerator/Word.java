package com.sixfingers.cardgenerator;

/**
 * Created by tatung on 6/8/15.
 */
public class Word {
    private String _word;
    private String _onYomi;
    private String _kunYomi;
    private String _meaningViet1;
    private String _meaningViet2;
    private String _meaningEng;

    public Word(String _word, String _onYomi, String _kunYomi, String _meaningViet1, String _meaningViet2, String _meaningEng) {
        this._word = _word;
        this._onYomi = _onYomi;
        this._kunYomi = _kunYomi;
        this._meaningViet1 = _meaningViet1;
        this._meaningViet2 = _meaningViet2;
        this._meaningEng = _meaningEng;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public String get_onYomi() {
        return _onYomi;
    }

    public void set_onYomi(String _onYomi) {
        this._onYomi = _onYomi;
    }

    public String get_kunYomi() {
        return _kunYomi;
    }

    public void set_kunYomi(String _kunYomi) {
        this._kunYomi = _kunYomi;
    }

    public String get_meaningViet1() {
        return _meaningViet1;
    }

    public void set_meaningViet1(String _meaningViet1) {
        this._meaningViet1 = _meaningViet1;
    }

    public String get_meaningViet2() {
        return _meaningViet2;
    }

    public void set_meaningViet2(String _meaningViet2) {
        this._meaningViet2 = _meaningViet2;
    }

    public String get_meaningEng() {
        return _meaningEng;
    }

    public void set_meaningEng(String _meaningEng) {
        this._meaningEng = _meaningEng;
    }
}
