using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using KarYab.Models;
using System.Data.Entity;
namespace KarYab.Controllers
{
    public class ExperienceApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetExperience(EXPERIENCE Experience, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.EXPERIENCES.Add(Experience);
                    db.SaveChanges();
                    OWNERSEXPERIENCE owenersExperience = new OWNERSEXPERIENCE();
                    owenersExperience.EXPERIENCEID = Experience.ID;
                    owenersExperience.EXPERIENCEOWNERID = HumanID;
                    db.OWNERSEXPERIENCES.Add(owenersExperience);
                    db.SaveChanges();
                    return Experience.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public EXPERIENCE GetExperience(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSEXPERIENCES.Any(r => r.EXPERIENCEOWNERID.Equals(HumanID) && r.EXPERIENCEID.Equals(ID)))
                    {
                        return db.EXPERIENCES.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new EXPERIENCE();
                    }

                }
                catch
                {
                    return new EXPERIENCE();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateExperience(EXPERIENCE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.EXPERIENCES.Attach(Entity);
                    var Entry = db.Entry(Entity);
                    Entry.State = EntityState.Modified;
                    db.SaveChanges();
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool DeleteExperience(EXPERIENCE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    EXPERIENCE entity = db.EXPERIENCES.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSEXPERIENCE Owner = db.OWNERSEXPERIENCES.FirstOrDefault(q => q.EXPERIENCEID == Entity.ID);
                    db.OWNERSEXPERIENCES.Attach(Owner);
                    db.OWNERSEXPERIENCES.Remove(Owner);
                    db.SaveChanges();
                    db.EXPERIENCES.Attach(entity);
                    db.EXPERIENCES.Remove(entity);
                    db.SaveChanges();

                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public List<EXPERIENCE> GetHumanExperiences(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> experienceIds = new List<long>();
                    experienceIds = db.OWNERSEXPERIENCES.Where(r => r.EXPERIENCEOWNERID.Equals(HumanID)).Select(r => r.EXPERIENCEID).ToList();
                    List<EXPERIENCE> experiences = new List<EXPERIENCE>();
                    foreach (long item in experienceIds)
                    {
                        var experience = db.EXPERIENCES.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        experiences.Add(experience);
                    }
                    return experiences;
                }
                catch(Exception e)
                {
                    return new List<EXPERIENCE>();
                }
            }
        }
    }
}
