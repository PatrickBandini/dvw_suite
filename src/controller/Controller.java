package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.DVW;
import view.View;

public class Controller {
	private View view;
	private DVW model;
	
	private File file;
	
	public Controller(View view, DVW model) {
		this.view = view;
		this.model = model;
		
		setUpViewEvents();
	}
	
	private void setUpViewEvents() {
		view.getOpenButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				file = view.chooseFile();
				if (null != file) {
					readFile(file);
				}
			}
		});
	}
	
	private void readFile(File f) {
		try {
			List<String> rows = Files.readAllLines(Paths.get(f.getPath()), StandardCharsets.ISO_8859_1);
			System.out.println(f.getPath());
			for(String r:rows) {
				System.out.println(r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
