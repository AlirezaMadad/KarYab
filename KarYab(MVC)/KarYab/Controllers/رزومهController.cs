using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using KarYab.Models;
using KarYab.Models.ViewModel;
using KarYab.Models.Enum;
namespace KarYab.Controllers
{
    public class رزومهController : Controller
    {
        //
        // GET: /رزومه/
        public ActionResult مشاهده(long HumanId)
        {
            ViewBag.HumanID = HumanId;
            ResumeViewModel Resume = new ResumeViewModel();
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                HUMAN human = db.HUMen.FirstOrDefault(q => q.ID == HumanId);
                Resume.NAME = human.NAME;
                Resume.GENDER = human.GENDER;
                Resume.USAGE = human.USAGE;
                //Resume.Image = db.IMAGES.FirstOrDefault(q => q.HUMANID == HumanId).IMAGE1;
                List<long> ExperiencesIds = db.OWNERSEXPERIENCES.Where(q=>q.EXPERIENCEOWNERID == HumanId).Select(q=>q.EXPERIENCEID).ToList();
                Resume.Experiences = db.EXPERIENCES.Where(q=>ExperiencesIds.Contains(q.ID)).ToList();
                List<long> EducationIds = db.OWNERSEDUCATIONS.Where(q => q.EDUCATIONOWNERID == HumanId).Select(q => q.EDUCATIONID).ToList();
                Resume.Educations = db.EDUCATONS.Where(q => EducationIds.Contains(q.ID)).ToList();
                List<long> SkillIlds = db.OWNERSSKILLS.Where(q => q.SKILLOWNERID == HumanId).Select(q => q.SKILLID).ToList();
                Resume.Skills = db.SKILLS.Where(q => SkillIlds.Contains(q.ID)).ToList();
                List<long> AdressIds = db.OWNERSADRESES.Where(q => q.ADRESSOWNERID == HumanId).Select(q => q.ADRESSID).ToList();
                Resume.Adresses = db.ADRESSES.Where(q => AdressIds.Contains(q.ID)).ToList();
                List<long> PhoneIds = db.OWNERSPHOES.Where(q => q.PHONEOWNERID == HumanId).Select(q => q.PHONENUMBERID).ToList();
                Resume.Phones = db.PHONES.Where(q => PhoneIds.Contains(q.ID)).ToList();
                List<long> CellPhoneIds = db.OWNERSCELLPHONES.Where(q => q.CELLPHONEOWNERID == HumanId).Select(q => q.CELLPHONEID).ToList();
                Resume.Cellphones = db.CELLPHONES.Where(q => CellPhoneIds.Contains(q.ID)).ToList();
                List<long> EmailIds = db.OWNERSMAILS.Where(q => q.MAILOWNERID == HumanId).Select(q => q.MAILADRESSID).ToList();
                Resume.Emails = db.MAILADRESSES.Where(q => EmailIds.Contains(q.ID)).ToList();
            }
            return View(Resume);
        }
	}
}