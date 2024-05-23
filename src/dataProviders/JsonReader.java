package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import managers.FileReaderManager;
import managers.Log;

public class JsonReader {
	private final String _linocutGalleryDataPath = FileReaderManager.getInstance()
			.getConfigReader().getTestDataPath() + "LinocutGalleryData.json";
	private final String _PhotographyGalleryDataPath = FileReaderManager.getInstance()
			.getConfigReader().getTestDataPath() + "PhotographyGalleryData.json";
	private final String _ukeAndTubaDataPath = FileReaderManager.getInstance()
			.getConfigReader().getTestDataPath() + "UkeAndTubaGalleryData.json";
	private final String _urlDataPath = FileReaderManager.getInstance()
			.getConfigReader().getTestDataPath() + "urlData.json";	
	
	private List<GalleryData> _linocutGalleryDataList;
	private List<GalleryData> _photographyGalleryDataList;
	private List<GalleryData> _ukeAndTubaGalleryDataList;
	private List<UrlData> _urlDataList;
	
	public JsonReader () {
		
		_linocutGalleryDataList = getLinocutGalleryData();
		_photographyGalleryDataList = getPhotographyGalleryData();
		_ukeAndTubaGalleryDataList = getUkeAndTubaGalleryData();
		_urlDataList=getUrlData();
		
	}

	private List<GalleryData> getLinocutGalleryData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		Log.info("_linocutGalleryDataPath="+ _linocutGalleryDataPath);
		try {
			bufferReader = new BufferedReader(new FileReader(_linocutGalleryDataPath));
			GalleryData[] imageDetails = gson.fromJson(bufferReader, GalleryData[].class );
			return Arrays.asList(imageDetails);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("missing linocut gallery data json");
		} finally {
				try {
					if (bufferReader!=null) {bufferReader.close();}
				}catch(IOException ignore){	
				}
		}
	}
	
	private List<GalleryData> getPhotographyGalleryData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(_PhotographyGalleryDataPath));
			GalleryData[] imageDetails = gson.fromJson(bufferReader, GalleryData[].class );
			return Arrays.asList(imageDetails);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("missing photography gallery data json");
		} finally {
				try {
					if (bufferReader!=null) {bufferReader.close();}
				}catch(IOException ignore){	
				}
		}
	}
	
	private List<GalleryData> getUkeAndTubaGalleryData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(_ukeAndTubaDataPath));
			GalleryData[] imageDetails = gson.fromJson(bufferReader, GalleryData[].class );
			return Arrays.asList(imageDetails);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("missing uke and tuba gallery data json");
		} finally {
				try {
					if (bufferReader!=null) {bufferReader.close();}
				}catch(IOException ignore){	
				}
		}
	}
	
	private List<UrlData> getUrlData() {
		Gson gson = new Gson();
		BufferedReader bufferReader = null;
		Log.info("_urlDataPath="+ _urlDataPath);
		try {
			bufferReader = new BufferedReader(new FileReader(_urlDataPath));
			UrlData[] urls = gson.fromJson(bufferReader, UrlData[].class );
			return Arrays.asList(urls);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("missing url data json");
		} finally {
				try {
					if (bufferReader!=null) {bufferReader.close();}
				}catch(IOException ignore){	
				}
		}
	}
	public final UrlData getUrls() {
		return _urlDataList.stream().findAny().get();
	}
	
	public final GalleryData getLinocutGalleryImageByImageNumber(String imageNumber) {
		return _linocutGalleryDataList.stream().filter(x -> x.imageNumber.equals(imageNumber)).findAny().get();
	}
	
	public final GalleryData getPhotographyGalleryImageByImageNumber(String imageNumber) {
		return _photographyGalleryDataList.stream().filter(x -> x.imageNumber.equals(imageNumber)).findAny().get();
	}
	
	public final GalleryData getUkeAndTubaGalleryImageByImageNumber(String imageNumber) {
		return _ukeAndTubaGalleryDataList.stream().filter(x -> x.imageNumber.equals(imageNumber)).findAny().get();
	}	
}