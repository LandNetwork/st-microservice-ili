package com.ai.st.microservice.ili.business;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ai.st.microservice.ili.dto.QueryDto;
import com.ai.st.microservice.ili.dto.QueryTypeDto;
import com.ai.st.microservice.ili.dto.VersionDataDto;
import com.ai.st.microservice.ili.dto.VersionDto;
import com.ai.st.microservice.ili.entities.ConceptEntity;
import com.ai.st.microservice.ili.entities.ModelEntity;
import com.ai.st.microservice.ili.entities.QueryEntity;
import com.ai.st.microservice.ili.entities.QueryTypeEntity;
import com.ai.st.microservice.ili.entities.VersionConceptEntity;
import com.ai.st.microservice.ili.entities.VersionEntity;
import com.ai.st.microservice.ili.exceptions.BusinessException;
import com.ai.st.microservice.ili.services.IConceptService;
import com.ai.st.microservice.ili.services.IVersionService;
import com.ai.st.microservice.ili.services.Ili2JsonService;

import ch.interlis.ili2c.metamodel.TransferDescription;

@Component
public class Ili2JsonBusiness {

	@Value("${iliProcesses.uploadedFiles}")
	private String uploadedFiles;

	@Value("${iliProcesses.downloadedFiles}")
	private String downloadedFiles;

	@Value("${iliProcesses.iliDirectory}")
	private String iliDirectory;

	@Value("${iliProcesses.iliDirectoryPlugins}")
	private String iliDirectoryPlugins;

	@Value("${iliProcesses.ogrPath}")
	private String ogrPath;

	public String ili2Json(MultipartFile[] uploadfiles, MultipartFile[] iliFiles) throws IOException {
		Ili2JsonService ili2json = new Ili2JsonService();
		ili2json.setEnv(uploadedFiles, downloadedFiles, iliDirectory, iliDirectoryPlugins, ogrPath);

		// Create temporal directory
		String tmpDirectoryPrefix = "ili2json_";
		Path tmpDirectory = Files.createTempDirectory(Paths.get(uploadedFiles), tmpDirectoryPrefix);
		String out = "";

		Map classesModels = new HashMap();

		// Upload model files
		for (MultipartFile iliFile : iliFiles) {
			String iliFileName = iliFile.getOriginalFilename();
			if (!iliFileName.equals("")) {
				String ilifilepath = Paths.get(tmpDirectory.toString(), iliFileName).toString();

				try ( // Save the file locally
						BufferedOutputStream ilistream = new BufferedOutputStream(
								new FileOutputStream(new File(ilifilepath)))) {
					ilistream.write(iliFile.getBytes());
				}

				TransferDescription td = ili2json.getTansfDesc(ilifilepath); // Convert ili 2 imd

				if (td != null) {

					Map classes = ili2json.getClassesTransDesc(td,
							iliFileName.substring(0, iliFileName.lastIndexOf('.')));

					if (!classes.isEmpty()) {
						classesModels.putAll(classes);
					}

					ili2json.td2imd(ilifilepath, td);
				}
			}
		}

		// upload xtf
		for (MultipartFile uploadfile : uploadfiles) {

			// Get the filename and build the local file path
			String fileXTF = uploadfile.getOriginalFilename();
			String pathXTF = Paths.get(tmpDirectory.toString(), fileXTF).toString();
			String workingDir = tmpDirectory.toString();

			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(pathXTF)))) {
				stream.write(uploadfile.getBytes());
			}

			ArrayList<String> iliModels = ili2json.getIliModels(pathXTF);

			iliModels.forEach((iliModel) -> {

				TransferDescription td = ili2json.getTansfDesc(iliModel);

				if (td != null) {

					ili2json.td2imd(iliModel, td, workingDir);
					String nameIliModel = new File(iliModel).getName();
					Map classes = ili2json.getClassesTransDesc(td,
							nameIliModel.substring(0, nameIliModel.lastIndexOf('.')));
					if (!classes.isEmpty()) {
						classesModels.putAll(classes);
					}
				}
			});

			List outFiles = ili2json.translate(pathXTF, classesModels);

			// check generate files
			HashMap items = ili2json.checkGenerateFile(outFiles);

			// get output to write
			out += ili2json.writeOutIli2Json(tmpDirectory.getFileName().getName(0).toString(), fileXTF, items);

		}

		if (out.lastIndexOf(",") != -1) {
			out = out.substring(0, out.lastIndexOf(","));
		}
		return out;
	}

}
