package com.ginkgocap.parasol.oauth2.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ginkgocap.parasol.oauth2.domain.dto.OauthClientDetailsDto;
import com.ginkgocap.parasol.oauth2.service.OauthService;
import com.ginkgocap.parasol.oauth2.web.oauth.OauthClientDetailsDtoValidator;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

/**
 * Handle 'client_details' management
 *
 * @author Shengzhao Li
 */
@Controller
public class ClientDetailsController {


    @Autowired
    private OauthService oauthService;
    
    @Autowired
    private UserLoginRegisterService userLoginRegisterService;

    @Autowired
    private OauthClientDetailsDtoValidator clientDetailsDtoValidator;


    @RequestMapping("userLoginRegisterService")
    public String getUserLoginRegister() {
    	try {
			userLoginRegisterService.getUserLoginRegister("13677687632");
			
		} catch (UserLoginRegisterServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    @RequestMapping("client_details")
    public String clientDetails(Model model) {
        List<OauthClientDetailsDto> clientDetailsDtoList = oauthService.loadAllOauthClientDetailsDtos();
        model.addAttribute("clientDetailsDtoList", clientDetailsDtoList);
        return "clientdetails/client_details";
    }


    /*
    * Logic delete
    * */
    @RequestMapping("archive_client/{clientId}")
    public String archiveClient(@PathVariable("clientId") String clientId) {
        oauthService.archiveOauthClientDetails(clientId);
        return "redirect:../client_details";
    }

    /*
    * Test client
    * */
    @RequestMapping("test_client/{clientId}")
    public String testClient(@PathVariable("clientId") String clientId, Model model) {
        OauthClientDetailsDto clientDetailsDto = oauthService.loadOauthClientDetailsDto(clientId);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        return "clientdetails/test_client";
    }


    /*
    * Register client
    * */
    @RequestMapping(value = "register_client", method = RequestMethod.GET)
    public String registerClient(Model model) {
        model.addAttribute("formDto", new OauthClientDetailsDto());
        return "clientdetails/register_client";
    }


    /*
    * Submit register client
    * */
    @RequestMapping(value = "register_client", method = RequestMethod.POST)
    public String submitRegisterClient(@ModelAttribute("formDto") OauthClientDetailsDto formDto, BindingResult result) {
        clientDetailsDtoValidator.validate(formDto, result);
        if (result.hasErrors()) {
            return "clientdetails/register_client";
        }
        oauthService.registerClientDetails(formDto);
        return "redirect:client_details";
    }


}