package com.jha.abhishek.hackernews.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DATA")
public class HackernewsStories implements Serializable {
	private static final long serialVersionUID = -1;

	@Id
	@Column(name = "id")
	Long id;
	@Column(name = "title")
	String title;
	@Column(name = "url")
	String url;
	@Column(name = "time")
	Timestamp time;
	@Column(name = "by")
	String by;
	@Column(name = "score")
	Long score;
	@Column(name = "type")
	String type;

	public HackernewsStories() {
	}

	public HackernewsStories(Long id, String title, String url, Timestamp time, String by, Long score, String type) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.time = time;
		this.by = by;
		this.score = score;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "HackernewsStories [id=" + id + ", title=" + title + ", url=" + url + ", time=" + time + ", by=" + by
				+ ", score=" + score + ", type=" + type + "]";
	}

}
