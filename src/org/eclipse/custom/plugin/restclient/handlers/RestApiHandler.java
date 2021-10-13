package org.eclipse.custom.plugin.restclient.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class RestApiHandler extends AbstractHandler {

	private static final String CURRENT_TIME = "Current Time";
	private static final String SERVICE_UPTIME = "Service Uptime";

	public static String ERROR_MESSAGE = "";

	private static final String GET_CURRENT_TIME_URL = "http://127.0.0.1:12345/currentTime";
	private static final String GET_SERVICE_UPTIME_URL = "http://127.0.0.1:12345/serviceUptime";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MyTitleAreaDialog dialog = new MyTitleAreaDialog(window.getShell());
		String response = "";

		int val = dialog.open();

		if (val == 0) {
			String selectedOption = dialog.getSelectedValue();

			if (selectedOption.equalsIgnoreCase(CURRENT_TIME)) {

				response = getResponse(GET_CURRENT_TIME_URL);

				if (!response.isBlank()) {
					openResponseWindow(window.getShell(), response);
				} else {
					openErrorWindow(window.getShell(), ERROR_MESSAGE);
				}
			} else if (selectedOption.equalsIgnoreCase(SERVICE_UPTIME)) {

				response = getResponse(GET_SERVICE_UPTIME_URL);

				if (!response.isBlank()) {
					openResponseWindow(window.getShell(), response);
				} else {
					openErrorWindow(window.getShell(), ERROR_MESSAGE);
				}
			}
		}
		return null;
	}

	private String getResponse(String urlToConnect) {
		URL url;
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String response = "";
		try {
			url = new URL(urlToConnect);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + connection.getResponseCode());
			}

			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			reader = new BufferedReader(in);
			String responseReceived = "";

			while ((responseReceived = reader.readLine()) != null) {
				response = responseReceived;
			}

			connection.disconnect();

		} catch (MalformedURLException ex) {
			ERROR_MESSAGE = ex.getMessage();
		} catch (ProtocolException ex) {
			ERROR_MESSAGE = ex.getMessage();
		} catch (IOException ex) {
			ERROR_MESSAGE = ex.getMessage();
		}

		return response;

	}

	private void openResponseWindow(Shell shell, String response) {
		MessageDialog.openInformation(shell, "Server Response", response);

	}

	private void openErrorWindow(Shell shell, String msg) {
		MessageDialog.openError(shell, "Error", msg);

	}

}
