package com.jha.abhishek.hackernews.domains;

public class HackernewsDomainByUser extends HackernewsDomain {

	String by;

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	@Override
	public String toString() {
		return "HackernewsDomainByUser [id=" + id + ", title=" + title + ", url=" + url + ", time=" + time + ", score="
				+ score + ", by=" + by + "]";
	}
}
