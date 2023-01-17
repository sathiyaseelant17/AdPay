package com.fab.adpay.spring.filters.requestPayloadValidation;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class RequestPayloadFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(RequestPayloadFilter.class);
	private static final String HTML_REGEX = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
	private static final Pattern HTML_PATTERN = Pattern.compile(HTML_REGEX);

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		LOG.info("RequestPayloadFilter.doFilterInternal() called");

		CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(
				httpServletRequest);
		InputStream inputStream = cachedBodyHttpServletRequest.getInputStream();
		String requestPayload = new String(StreamUtils.copyToByteArray(inputStream));
		try {
			if (!requestPayload.isEmpty() && (hasHTMLTags(requestPayload) || hasHTMLEncoded(requestPayload))) {
				LOG.info("requestPayload \n {}", requestPayload);
				throw new Exception("Do not send HTML tags in Request Payload");
			}
		} catch (Exception e) {
			LOG.error("Input Validation Exception Occured {}", e);
			httpServletResponse.getWriter()
					.write("{\"errorCode\":\"212\",\"errorText\":\"Do not send HTML tags in Request Payload\"}");

			return;
		}

		filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
	}

	private boolean hasHTMLEncoded(String requestPayload) throws UnsupportedEncodingException {
		boolean isHtmlEncoded = false;
		String decodedHtml = URLDecoder.decode(requestPayload, "UTF-8");
		LOG.info("decodedHtml : {}", decodedHtml);
		isHtmlEncoded = hasHTMLTags(decodedHtml);
		LOG.info("isHtmlEncoded : {}", isHtmlEncoded);
		return isHtmlEncoded;
	}

	public boolean hasHTMLTags(String text) {
		return HTML_PATTERN.matcher(text).find();
	}
}