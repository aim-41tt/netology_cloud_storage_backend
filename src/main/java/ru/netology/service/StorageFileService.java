package ru.netology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dto.request.EditFileNameRQ;
import ru.netology.dto.response.FileRS;
import ru.netology.exception.InputDataException;
import ru.netology.exception.UnauthorizedException;
import ru.netology.model.StorageFile;
import ru.netology.model.User;
import ru.netology.repository.AuthenticationRepository;
import ru.netology.repository.StorageFileRepository;
import ru.netology.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StorageFileService {
	private static final Logger log = LoggerFactory.getLogger(StorageFileService.class);
	private StorageFileRepository storageFileRepository;
	private AuthenticationRepository authenticationRepository;
	private UserRepository userRepository;

	public StorageFileService(StorageFileRepository storageFileRepository,
			AuthenticationRepository authenticationRepository, UserRepository userRepository) {
		this.storageFileRepository = storageFileRepository;
		this.authenticationRepository = authenticationRepository;
		this.userRepository = userRepository;
	}

	public boolean uploadFile(String authToken, String filename, MultipartFile file) {
		final User user = getUserByAuthToken(authToken);
		if (user == null) {
			log.error("Upload file: Unauthorized");
			throw new UnauthorizedException("Upload file: Unauthorized");
		}
		try {
			StorageFile storageFilenew = new StorageFile();
			storageFilenew.setFilename(filename);
			storageFilenew.setDate(LocalDateTime.now());
			storageFilenew.setFileContent(file.getBytes());
			storageFilenew.setSize(Long.valueOf(file.getSize()));
			storageFilenew.setUser(user);
			System.err.println(storageFilenew.getFileContent().getClass().getName());
			storageFileRepository.save(storageFilenew);
			log.info("Success upload file. User {}", user.getUsername());
			return true;
		} catch (IOException e) {
			log.error("Upload file: Input data exception");
			throw new InputDataException("Upload file: Input data exception");
		}
	}

	@Transactional
	public void deleteFile(String authToken, String filename) {
		final User user = getUserByAuthToken(authToken);
		if (user == null) {
			log.error("Delete file: Unauthorized");
			throw new UnauthorizedException("Delete file: Unauthorized");
		}

		storageFileRepository.deleteByUserAndFilename(user, filename);

		final StorageFile tryingToGetDeletedFile = storageFileRepository.findByUserAndFilename(user, filename);
		if (tryingToGetDeletedFile != null) {
			log.error("Delete file: Input data exception");
			throw new InputDataException("Delete file: Input data exception");
		}
		log.info("Success delete file. User {}", user.getUsername());
	}

	public byte[] downloadFile(String authToken, String filename) {
		final User user = getUserByAuthToken(authToken);
		if (user == null) {
			log.error("Download file: Unauthorized");
			throw new UnauthorizedException("Download file: Unauthorized");
		}

		final StorageFile file = storageFileRepository.findByUserAndFilename(user, filename);
		if (file == null) {
			log.error("Download file: Input data exception");
			throw new InputDataException("Download file: Input data exception");
		}
		log.info("Success download file. User {}", user.getUsername());
		return file.getFileContent();
	}

	@Transactional
	public void editFileName(String authToken, String filename, EditFileNameRQ editFileNameRQ) {
		final User user = getUserByAuthToken(authToken);
		if (user == null) {
			log.error("Edit file name: Unauthorized");
			throw new UnauthorizedException("Edit file name: Unauthorized");
		}

		storageFileRepository.editFileNameByUser(user, filename, editFileNameRQ.getFilename());

		final StorageFile fileWithOldName = storageFileRepository.findByUserAndFilename(user, filename);
		if (fileWithOldName != null) {
			log.error("Edit file name: Input data exception");
			throw new InputDataException("Edit file name: Input data exception");
		}
		log.info("Success edit file name. User {}", user.getUsername());
	}

	public List<FileRS> getAllFiles(String authToken, Integer limit) {
		final User user = getUserByAuthToken(authToken);
		if (user == null) {
			log.error("Get all files: Unauthorized");
			throw new UnauthorizedException("Get all files: Unauthorized");
		}
		log.info("Success get all files. User {}", user.getUsername());
		return storageFileRepository.findAllByUser(user).stream().map(o -> new FileRS(o.getFilename(), o.getSize()))
				.collect(Collectors.toList());
	}

	private User getUserByAuthToken(String authToken) {
		if (authToken.startsWith("Bearer ")) {
			final String authTokenWithoutBearer = authToken.split(" ")[1];
			final String username = authenticationRepository.getUsernameByToken(authTokenWithoutBearer);
			return userRepository.findByUsername(username);
		}
		return null;
	}
}