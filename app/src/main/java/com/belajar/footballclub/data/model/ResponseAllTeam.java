package com.belajar.footballclub.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAllTeam{

	@SerializedName("teams")
	private List<TeamsItem> teams;

	public List<TeamsItem> getTeams(){
		return teams;
	}
}