package com.jha.abhishek.hackernews.domains;

import java.sql.Time;;

public class HackernewsDomain {

	Long id;
	String title;
	String url;
	Time time;
	Long score;

	public HackernewsDomain() {
		super();
	}

	public HackernewsDomain(Long id, String title, String url, Time time, Long score) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.time = time;
		this.score = score;
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

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		//Date d = new Date(time*1000L);
		this.time = time;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "HackernewsDomain [id=" + id + ", title=" + title + ", url=" + url + ", time=" + time + ", score="
				+ score + "]";
	}
}
