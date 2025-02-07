package com.naitech.cursomc.controllers.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String s){
		return Arrays.asList(s.split(",")).stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
	}
}
