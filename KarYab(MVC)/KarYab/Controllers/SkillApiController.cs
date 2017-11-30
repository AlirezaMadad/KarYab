using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Mvc;
using KarYab.Models;
using System.Data.Entity;
namespace KarYab.Controllers
{
    public class SkillApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetSkill(SKILL Skill, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.SKILLS.Add(Skill);
                    db.SaveChanges();
                    OWNERSSKILL owenersSkill = new OWNERSSKILL();
                    owenersSkill.SKILLID = Skill.ID;
                    owenersSkill.SKILLOWNERID = HumanID;
                    db.OWNERSSKILLS.Add(owenersSkill);
                    db.SaveChanges();
                    return Skill.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public SKILL GetSkill(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSSKILLS.Any(r => r.SKILLOWNERID.Equals(HumanID) && r.SKILLID.Equals(ID)))
                    {
                        return db.SKILLS.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new SKILL();
                    }

                }
                catch
                {
                    return new SKILL();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateSkill(SKILL Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                   // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.SKILLS.Attach(Entity);
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
        public bool DeleteSkill(SKILL Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    //var employer = new Employ { Id = 1 };
                    //ctx.Employ.Attach(employer);
                    //ctx.Employ.Remove(employer);
                    //ctx.SaveChanges();
                    SKILL entity = db.SKILLS.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSSKILL Owner = db.OWNERSSKILLS.FirstOrDefault(q => q.SKILLID == Entity.ID);
                    db.OWNERSSKILLS.Attach(Owner);
                    db.OWNERSSKILLS.Remove(Owner);
                    db.SaveChanges();
                    db.SKILLS.Attach(entity);
                    db.SKILLS.Remove(entity);
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
        public List<SKILL> GetHumanSkills(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> skillIds = new List<long>();
                    skillIds = db.OWNERSSKILLS.Where(r => r.SKILLOWNERID.Equals(HumanID)).Select(r => r.SKILLID).ToList();
                    List<SKILL> skills = new List<SKILL>();
                    foreach (long item in skillIds)
                    {
                        skills.Add(db.SKILLS.Where(r => r.ID.Equals(item)).Single());
                    }
                    return skills;
                }
                catch
                {
                    return new List<SKILL>();
                }
            }
        }
        
    }
}
