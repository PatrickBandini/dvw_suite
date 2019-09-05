package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.DVW;
import model.Riga;
import view.View;

public class Controller {
	private View view;
	private DVW model;
	
	private File originalFile;
	
	public Controller(View view, DVW model) {
		this.view = view;
		this.model = model;
		
		setUpViewEvents();
	}
	
	private void setUpViewEvents() {
		view.getOpenButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				originalFile = null;
				originalFile = view.chooseFile();
				if (null != originalFile) {
					readFile(originalFile);
				}
			}
		});
		
		view.getPre().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = view.getPre().getText().substring(8);
				File toSave = view.saveFile(filename);
				writeFile(toSave);
			}
		});
		
		view.getPost().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = view.getPost().getText().substring(8);
				System.out.println(filename);
				
			}
		});
	}
	
	private void readFile(File f) {
		try {
			this.model = new DVW(f);
			List<String> rows = Files.readAllLines(Paths.get(f.getPath()), StandardCharsets.ISO_8859_1);
			List<Riga> righe = new ArrayList<Riga>();
			for(int i=0;i<rows.size(); i++) {
				if ("[3SCOUT]".equals(rows.get(i))) {
					List<String> intestazione = rows.subList(0, i+1);
					model.setIntestazione(intestazione, i+1);
				}
				if (i >= model.getIndexFineIntestazione()) {
					righe.add(new Riga(rows.get(i)));
				}
			}
			model.setRighe(righe);
			model.tempiAlzataCambioPalla();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeFile(File toSave) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(toSave), StandardCharsets.ISO_8859_1);
			List<String> rows = Files.readAllLines(Paths.get(model.getFile().getPath()), StandardCharsets.ISO_8859_1);
			for(String r:rows) {
				osw.write(r + "\n");
			}
			osw.flush();
			osw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
